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

import axios from 'axios'

export const getServiceList = params => {
  return axios.get('/api/app', {params: params});
};

export const getProvider = params => {
  return axios.get('/api/provider/info', {params: params});
};
export const deleteProvider = params => {
  return axios.post('/api/provider/info', params);
};

export const updateProviderStatus = params => {
  return axios.put('/api/provider/status', params);
};

export const updateProviderWeight = params => {
  return axios.put('/api/provider/weight', params);
};

