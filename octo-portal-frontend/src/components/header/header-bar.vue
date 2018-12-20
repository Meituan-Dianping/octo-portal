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
    <sider-active :isCollapsed="isCollapsed" icon="md-menu" @on-change="handleCollpasedChange"></sider-active>
    <diy-bread-crumb show-icon style="margin-left: 32px;" :list="headerBreadCrumbList"></diy-bread-crumb>
  </div>
</template>
<script>
import siderActive from './sider-active'
import diyBreadCrumb from './diy-bread-crumb'

export default {
  name: 'HeaderBar',
  components: {
    siderActive,
    diyBreadCrumb
  },
  props: {
    isCollapsed: Boolean
  },
  computed: {
    headerBreadCrumbList () {
      return this.$store.state.headerBreadCrumbList
    }
  },
  methods: {
    handleCollpasedChange (state) {
      this.$emit('on-collapsed-change', state)
    }
  }
}
</script>
