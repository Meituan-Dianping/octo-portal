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
  <a @click="collapsedChange" type="text" :class="['sider-trigger', isCollapsed ? 'collapsed' : '']"><Icon :type="icon" :size="size" /></a>
</template>
<script>
export default {
  name: 'SiderActive',
  props: {
    isCollapsed: Boolean,
    icon: {
      type: String,
      default: 'navicon-round'
    },
    size: {
      type: Number,
      default: 27
    }
  },
  methods: {
    collapsedChange () {
      this.$emit('on-change', !this.isCollapsed)
    }
  }
}
</script>
<style scoped lang="less">
  .sider-trigger{
    width: 50px;
    heighpxt: 50px;
    padding: 4px;
    display: inline-block;
    text-align: center;
    color: #5d6a76;
    margin-top: 14px;
    i{
      vertical-align: top;
      transition: transform .1s ease;
    }
    &.collapsed i{
      transform: rotateZ(90deg);
      transition: transform .1s ease;
    }
  }
</style>
