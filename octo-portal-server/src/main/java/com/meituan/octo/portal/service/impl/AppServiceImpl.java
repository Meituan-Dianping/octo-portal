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

package com.meituan.octo.portal.service.impl;

import com.meituan.octo.portal.service.AppService;
import com.meituan.octo.portal.service.curator.ZookeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AppServiceImpl implements AppService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppServiceImpl.class);
    private static final String ZK_ROOT_PATH = "/octo/nameservice";

    @Autowired
    private ZookeeperClient zkClient;

    @Override
    public List<String> getAppList() {
        String prodPath = ZK_ROOT_PATH + "/prod";
        try {
            List<String> appList = zkClient.getChildren(prodPath);
            return appList;
        } catch (Exception e) {
            LOGGER.error("get appList failed: {}", e);
            return Collections.emptyList();
        }
    }
}
