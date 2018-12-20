<!--
  - Copyright 2018 Meituan Dianping. All rights reserved.
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
  <div>
    <Card style="min-width: 800px">
      <Form :model="formData" inline  lable-position="right">
        <FormItem label="服务" :label-width="80">
          <Select v-model="formData.serviceName" style="width:250px">
            <Option v-for="item in serviceList" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="环境" :label-width="80">
          <RadioGroup v-model="formData.env" type="button">
            <Radio label="prod"></Radio>
            <Radio label="stage"></Radio>
            <Radio label="test"></Radio>
          </RadioGroup>
        </FormItem>
      </Form>
    </Card>
    <Card style="margin-top: 10px; min-width: 800px">
      <Table height="500" :columns="columnsConfig" :data="tableData"></Table>
    </Card>
    <Modal
      v-model="weightModal.visible"
      title="权重调整"
      width=300
      :closable="false"
      @on-ok="updateWeight"
      >
      <Input v-model=weightModal.updatedWeight placeholder="修改权重"/>
    </Modal>
  </div>
</template>
<script>
  import {
    getServiceList,
    getProvider,
    updateProviderStatus,
    updateProviderWeight,
    deleteProvider
  } from '@/api/api.js'
  import {formatUnixtimestamp} from '@/common/util.js'
  import config from '@/config'

  export default {
    name: 'provider',
    data () {
      return {
        formData: {
          serviceName: '',
          env: 'prod',
        },
        weightModal: {
          visible: false,
          row: '',
          updatedWeight: '',
          ip: '',
          port: ''
        },
        serviceList: [],
        columnsConfig: [
          {
            title: '主机',
            key: 'ip'
          },
          {
            title: '端口',
            key: 'port'
          },
          {
            title: '版本',
            key: 'version'
          },
          {
            title: '权重',
            key: 'weight',
          },
          {
            title: '状态',
            key: 'status',
            render: (h, params) => {
              let status = params.row.status;
              if(status === config.nodeStatus.alive) {
                return h('div', {style: {color: 'green'}}, "正常")
              }
              let text = "";
              switch (status) {
                case config.nodeStatus.dead:
                  text = '未启动';
                  break;
                case config.nodeStatus.starting:
                  text = '启动中';
                  break;
                case config.nodeStatus.stopping:
                  text = '关闭中';
                  break;
                default:
                  text = '禁用';
              }
              return h('div', {style: {color: 'red'}}, text)
            }
          },
          {
            title: '更新时间',
            key: 'lastUpdateTime',
            width: 150,
          },
          {
            title: '节点操作',
            width: 220,
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'success',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      if(params.row.status === config.nodeStatus.alive) {
                        this.showMessage('info', '节点已处于正常状态');
                      } else {
                        this.updateProviderStatus(params.row, this.formData.serviceName, params.row.ip, params.row.port, config.nodeStatus.alive);
                      }
                    }
                  }
                }, '启用'),

                h('Button', {
                  props: {
                    type: 'warning',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      if(params.row.status === config.nodeStatus.stopped) {
                        this.showMessage('info', '节点已处于禁用状态');
                      } else {
                        this.updateProviderStatus(params.row, this.formData.serviceName, params.row.ip, params.row.port, config.nodeStatus.stopped);
                      }
                    }
                  }
                }, '禁用'),

                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.weightModal.row = params.row;
                      this.weightModal.updatedWeight = params.row.weight;
                      this.weightModal.ip = params.row.ip;
                      this.weightModal.port = params.row.port;
                      this.weightModal.visible=true;
                    }
                  }
                }, '调权'),

                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.$Modal.confirm({
                        title: '确定删除节点？',
                        onOk: () => {
                          this.deleteProvider(this.formData.serviceName, params.row.ip, params.row.port);
                        },
                        onCancel: () => {}
                      });

                    }
                  }
                }, '删除')
              ]);
            }
          },
        ],
        tableData: [],
      }
    },
    watch: {
      formData:{
        handler(newVal, oldval) {
          this.searchProvider();
        },
        deep:true
      }
    },
    methods: {
      showMessage(type, content, duration = 1.5, closable = false) {
        this.$Message[type]({
          content: content,
          duration: duration,
          closable: closable
        })
      },
      searchProvider() {
        let params = {
          env: this.transEnv(this.formData.env),
          serviceName: this.formData.serviceName,
        };
        getProvider(params).then(res => {
          let providerDescArr = res.data.data;
          let descArr = [];
          providerDescArr.forEach(function (obj) {
            if(obj && obj.ip) {
              let desc = {};
              desc.ip = obj.ip;
              desc.port = obj.port;
              desc.version = obj.version;
              desc.weight = obj.fweight;
              desc.status = obj.status;
              desc.lastUpdateTime = formatUnixtimestamp(obj.lastUpdateTime);
              descArr.push(desc);
            }
          });
          this.tableData = descArr;
        }).catch(err => {});
      },
      updateProviderStatus(row, serviceName, ip, port, newStatus) {
        let params = {
          env: this.transEnv(this.formData.env),
          serviceName: serviceName,
          ip: ip,
          port: port,
          status: newStatus
        };
        let msg = newStatus === config.nodeStatus.alive ? "启用" : "禁用";
        updateProviderStatus(params).then(res => {
          if(res.data.success === true) {
            row.status = newStatus;
            row.lastUpdateTime = formatUnixtimestamp(Date.parse(new Date())/1000);
            this.showMessage("success", msg + "成功")
          } else {
            this.showMessage("error", msg + "失败，请重试")
          }
        }).catch(err => {})
      },
      updateWeight() {
        let params = {
          env: this.transEnv(this.formData.env),
          serviceName: this.formData.serviceName,
          ip: this.weightModal.ip,
          port: this.weightModal.port,
          weight: this.weightModal.updatedWeight
        };
        updateProviderWeight(params).then(res => {
          if(res.data.success === true) {
            this.weightModal.row.weight = this.weightModal.updatedWeight;
            this.weightModal.row.lastUpdateTime = formatUnixtimestamp(Date.parse(new Date())/1000);
            this.showMessage("success", "权重调整成功")
          } else {
            this.showMessage("error", "权重调整失败，请重试")
          }
        }).catch(err => {})
      },
      deleteProvider(serviceName, ip, port) {
        let params = {
          env: this.transEnv(this.formData.env),
          serviceName: serviceName,
          ip: ip,
          port: port
        };
        let that = this;
        deleteProvider(params).then(res => {
          if(res.data.success === true) {
            this.showMessage("success", "删除成功");
            that.searchProvider();
          } else {
            this.$Message.error('删除节点失败，请重试');
          }
        }).catch(err => {})
      },
      transEnv(env) {
        if(env === "prod") {
          return 3;
        } else if(env === "stage") {
          return 2;
        } else {
          return 1;
        }
      }
    },
    mounted () {
      getServiceList().then(res => {
        if(res.data.success === true) {
          let serviceArr = res.data.data;
          let optionData = [];
          serviceArr.forEach(function(service) {
            let item = {};
            item.value = service;
            item.label = service;
            optionData.push(item);
          });
          this.serviceList = optionData;
          this.formData.serviceName = optionData[0].value;
        } else {
          this.$Message.error('获取服务名列表失败');
        }
      }).catch(err => {})
    }
  }
</script>
