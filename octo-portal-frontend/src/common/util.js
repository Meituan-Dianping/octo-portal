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

export const formatUnixtimestamp =  (time) => {
  var timestamp = new Date(time * 1000);
  var year = 1900 + timestamp.getYear();
  var month = "0" + (timestamp.getMonth() + 1);
  var date = "0" + timestamp.getDate();
  var hour = "0" + timestamp.getHours();
  var minute = "0" + timestamp.getMinutes();
  var second = "0" + timestamp.getSeconds();
  return year + "-" + month.substring(month.length-2, month.length)  + "-" + date.substring(date.length-2, date.length)
    + " " + hour.substring(hour.length-2, hour.length) + ":"
    + minute.substring(minute.length-2, minute.length) + ":"
    + second.substring(second.length-2, second.length);
}

export const getSiderMenuByRouters = (list) => {
  let res = []
  for(let i = 0; i < list.length; i ++) {
    let item = list[i]
    let obj = {
      icon: (item.meta && item.meta.icon) || '',
      name: item.name,
      meta: item.meta
    }
    if (hasChild(item)) {
      obj.children = getSiderMenuByRouters(item.children)
    }
    res.push(obj)
  }
  return res
}

export const hasChild = (item) => {
  return item.children && item.children.length !== 0
}

export const getHeaderBreadCrumbList = (route) => {
  let routeArr = route.matched
  let headerBreadCrumbList = routeArr.map(item => {
    let headerBreadCrumb = {
      icon: (item.meta && item.meta.icon) || '',
      name: (item.meta && item.meta.title) || item.name,
      to: item.path
    }
    return headerBreadCrumb
  })
  return headerBreadCrumbList
}

export const showTitle = (item) => {
  return (item.meta && item.meta.title) || item.name
}
