/*
 * Copyright 2018 Meituan Dianping. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Main from '@/components/Main'

export default [
  {
    path: '/',
    name: '_provider',
    redirect: '/provider',
    meta: {
      icon: 'md-book',
      title: '服务管理'
    },
    component: Main,
    children: [
      {
        path: '/provider',
        name: 'provider',
        meta: {
          icon: 'md-folder',
          title: '服务提供者'
        },
        component: () => import('@/components/content/serviceManage/provider.vue')
      },
      {
        path: '/outline',
        name: 'outline',
        meta: {
          icon: 'md-notifications',
          title: '服务概要'
        },
        component: () => import('@/components/content/serviceManage/outline.vue')
      },

    ]
  },
  {
    path: '/data',
    name: 'data',
    redirect: '/performance',
    meta: {
      icon: 'md-create',
      title: '数据分析'
    },
    component: Main,
    children: [
      {
        path: '/performance',
        name: 'performance',
        meta: {
          icon: 'md-calculator',
          title: '性能指标'
        },
        component: () => import('@/components/content/performance/performance.vue')
      }
    ]
  },
  {
    path: '/extention1',
    name: 'extention1',
    redirect: '/extention1_1',
    meta: {
      icon: 'md-desktop',
      title: '扩展'
    },
    component: Main,
    children: [
      {
        path: '/extention1_1',
        name: 'extention1_1',
        meta: {
          icon: 'md-document',
          title: '扩展1'
        },
        component: () => import('@/components/content/extention-1/extention1_1.vue')
      },
      {
        path: '/extention1_2',
        name: 'extention1_2',
        meta: {
          icon: 'md-egg',
          title: '扩展2'
        },
        component: () => import('@/components/content/extention-1/extention1_2.vue')
      },
      {
        path: '/extention1_3',
        name: 'extention1_4',
        meta: {
          icon: 'md-flag',
          title: '扩展3'
        },
        component: () => import('@/components/content/extention-1/extention1_3.vue')
      }
    ]
  }
]
