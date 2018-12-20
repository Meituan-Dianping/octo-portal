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
  <Submenu :name="`${parentName}`">
    <template slot="title">
      <Icon :type="parentItem.icon || ''"/>
      <span>{{ showTitle(parentItem) }}</span>
    </template>
    <template v-for="item in childrenList">
      <sidebar-menu-item v-if="hasChildren(item)" :key="`menu-${item.name}`" :parent-item="item"></sidebar-menu-item>
      <MenuItem v-else :name="getName(item)" :key="`menu-${item.name}`"><Icon :type="item.icon || ''"/><span>{{ showTitle(item) }}</span></MenuItem>
    </template>
  </Submenu>
</template>
<script>
  import mixin from './mixin'

  export default {
    name: "sidebar-menu-item",
    mixins: [ mixin ],
    props: {
      parentItem: {
        type: Object,
        default: () => {}
      },
      theme: String,
      iconSize: Number
    },
    computed: {
      parentName () {
        return this.parentItem.name
      },
      childrenList () {
        return this.parentItem.children
      },
      textColor () {
        return this.theme === 'dark' ? '#fff' : '#495060'
      }
    }
  }

</script>
<style scoped>

</style>
