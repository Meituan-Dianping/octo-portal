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

import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

export default {
  bootstrap() {
    let mock = new MockAdapter(axios);
    mock.onGet('/test').reply(200, {
      city: [
        {
          value: 'New York',
          label: 'New York'
        },
        {
          value: 'Beijing',
          label: 'Beijing'
        }
      ]
    });

    mock.onGet('/provider').reply(200, {
      tableData: [
        {
          ip: '127.0.0.1',
          port: '8080',
          version: 'dorado-1.0',
          weight: '10',
          status: '1',
          lastUpdateTime: '1111'
        },
        {
          ip: '127.0.0.1',
          port: '8080',
          version: 'dorado-1.0',
          weight: '10',
          status: '1',
          lastUpdateTime: '1111'
        },
        {
          ip: '127.0.0.1',
          port: '8080',
          version: 'dorado-1.0',
          weight: '10',
          status: '1',
          lastUpdateTime: '1111'
        },
        {
          ip: '127.0.0.1',
          port: '8080',
          version: 'dorado-1.0',
          weight: '10',
          status: '1',
          lastUpdateTime: '1111'
        },
        {
          ip: '127.0.0.1',
          port: '8080',
          version: 'dorado-1.0',
          weight: '10',
          status: '1',
          lastUpdateTime: '1111'
        }
      ]
    })

  }
}
