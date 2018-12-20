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
  <div class="main">
    <Layout>
      <Sider  hide-trigger  collapsible :width="256" :collapsed-width="64" v-model="isCollapsed">
        <sidebar-menu accordion :active-name="$route.name" :isCollapsed="isCollapsed" @on-select="routeChange" :menu-list="menuList">
            <div class="custom-title" v-show="!isCollapsed" style="color:#FFFFFF; ">
              OCTO服务治理平台
            </div>
        </sidebar-menu>
      </Sider>
      <Layout>
        <Header class="header-wrap">
          <header-bar :isCollapsed="isCollapsed" @on-collapsed-change="handleCollapsedChange"></header-bar>
        </Header>
        <Content class="main-content-wrap">
          <Layout class="main-layout-wrap">
            <Content class="content-wrap">
              <router-view/>
            </Content>
          </Layout>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>
<script>
  import SidebarMenu from "./sider/sidebar-menu";
  import HeaderBar from './header/header-bar'
  import {mapMutations} from 'vuex'

  export default {
    components: {
      SidebarMenu,
      HeaderBar
    },
    data () {
      return {
        isCollapsed: false,
      }
    },
    computed: {
      menuList () {
        return this.$store.getters.menuList
      },
    },
    methods: {
      routeChange (route) {
        let { name, params, query } = {}
        if (typeof route === 'string') name = route
        else {
          name = route.name
          params = route.params
          query = route.query
        }
        this.$router.push({
          name,
          params,
          query
        })
      },
      handleCollapsedChange (state) {
        this.isCollapsed = state
      },
      ...mapMutations([
        'setHeaderBreadCrumb',
      ]),
    },
    watch: {
      '$route' (newRoute) {
        this.setHeaderBreadCrumb(newRoute)
      }
    },
    mounted () {
      this.setHeaderBreadCrumb(this.$route)
    }
  }
</script>

<style scoped lang = "less">
  .ivu-layout-sider {
    height: 100vh;
  }

  .main {
    .custom-title {
      height: 64px;
      padding: 10px;
      text-align: center;
      font-weight: bold;
      font-size: 20px;
      line-height: 50px;
    }
    .header-wrap {
      background: #fff;
      padding: 0 20px;
      width: 100%;
    }
    .main-content-wrap {
      height: calc(100% - 80px);
      overflow: hidden;
    }
    .main-layout-wrap {
      height: 100%;
      overflow: hidden;
    }
    .content-wrap {
      padding: 20px;
      height: calc(100% - 100px);
      overflow: auto;
    }
  }

</style>
