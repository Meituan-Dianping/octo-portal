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
    <div class = "sidebar-menu-wrapper">
      <slot></slot>
      <Menu ref="menu" v-show="!isCollapsed"  :theme="theme" :active-name="activeName" :open-names="openedNames" :accordion="accordion" width="auto" @on-select="handleSelect">
        <template v-for="item in menuList">
            <sidebar-menu-item v-if="hasChildren(item)" :key="`menu-${item.name}`" :parent-item="item"></sidebar-menu-item>
            <MenuItem v-else :name="getName(item)" :key="`menu-${item.name}`"><Icon :type="item.icon || ''"/><span>{{ showTitle(item) }}</span></MenuItem>
        </template>
      </Menu>

      <div class="sidebar-menu-collapsed" v-show="isCollapsed" :list="menuList">
        <template v-for="item in menuList">
          <sidebar-collapsed-menu v-if="hasChildren(item)" @on-click="handleSelect" hide-title :root-icon-size="rootIconSize" :icon-size="iconSize" :theme="theme" :parent-item="item" :key="`collapsed-menu-${item.name}`"></sidebar-collapsed-menu>
          <Tooltip transfer v-else :content="showTitle(item)" placement="right" :key="`collapsed-menu-${item.name}`">
            <a @click="handleSelect(getName(item, false))" class="drop-menu-a">
              <Icon :size="rootIconSize" :color="textColor" :type="item.icon || (item.children && item.children[0].icon)"/>
            </a>
          </Tooltip>
        </template>
      </div>
    </div>
</template>

<script>
  import SidebarMenuItem from './sidebar-menu-item'
  import SidebarCollapsedMenu from './sidebar-collapsed-menu.vue'
  import mixin from './mixin'

  export default {
    name: "sidebar-menu",
    components: {
      SidebarMenuItem,
      SidebarCollapsedMenu
    },
    mixins: [ mixin ],
    props: {
      isCollapsed: {
        type: Boolean
      },
      activeName: {
        type: String,
        default: ''
      },
      accordion: Boolean,
      menuList: {
        type: Array,
        default () {
          return []
        }
      },
      theme: {
        type: String,
        default: 'dark'
      },
      rootIconSize: {
        type: Number,
        default: 30
      },
      iconSize: {
        type: Number,
        default: 16
      },
    },
    data () {
      return {
        openedNames: []
      }
    },
    methods: {
      handleSelect (name) {
        this.$emit('on-select', name);
      }
    },
    computed: {
      textColor () {
        return this.theme === 'dark' ? '#fff' : '#495060'
      }
    },
  }
</script>

<style scoped lang="less">
  .margin {
    margin: 10px 0px;
  }
  .sidebar-menu-wrapper{
    user-select: none;
    .sidebar-menu-collapsed{
      padding-top: 10px;

      .ivu-dropdown{
        width: 100%;
        .margin;
        .ivu-dropdown-rel a{
          width: 100%;
        }
      }
      .ivu-tooltip{
        width: 100%;
        .ivu-tooltip-rel{
          width: 100%;
        }
      }
    }
    a.drop-menu-a{
      display: inline-block;
      width: 100%;
      padding: 8px 16px;
      text-align: center;
      color: #474e5e;
      .margin;
    }
  }
</style>
