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
  <Dropdown @on-click="handleClick"  :transfer="hideTitle" :placement="placement" :style="{textAlign: 'center'}">
    <a class="drop-menu-a" type="text"><Icon :size="rootIconSize"  :color="textColor" :type="parentItem.icon"/><span class="menu-title" v-if="!hideTitle">{{ showTitle(parentItem) }}</span></a>
    <DropdownMenu slot="list">
      <template v-for="child in children">
        <sidebar-collapsed-menu v-if="hasChildren(child)" :icon-size="iconSize" :parent-item="child" :key="`drop-${child.name}`"></sidebar-collapsed-menu>
        <DropdownItem v-else :key="`drop-${child.name}`" :name="child.name"><Icon :size="iconSize" :type="child.icon"/><span>{{ showTitle(child) }}</span></DropdownItem>
      </template>
    </DropdownMenu>
  </Dropdown>
</template>
<script>
import mixin from './mixin'

export default {
  name: 'SidebarCollapsedMenu',
  mixins: [ mixin ],
  props: {
    hideTitle: {
      type: Boolean,
      default: false
    },
    rootIconSize: {
      type: Number,
      default: 16
    }
  },
  data () {
    return {
      placement: 'right-end'
    }
  },
  methods: {
    handleClick (name) {
      this.$emit('on-click', name)
    },
  }
}
</script>
