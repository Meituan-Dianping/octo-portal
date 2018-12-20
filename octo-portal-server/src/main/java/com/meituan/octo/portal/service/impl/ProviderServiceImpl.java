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

import com.meituan.octo.portal.model.ProviderDesc;
import com.meituan.octo.portal.model.ProviderNodeData;
import com.meituan.octo.portal.service.ProviderService;
import com.meituan.octo.portal.service.curator.ZookeeperClient;
import com.meituan.octo.portal.util.ZkPathUtil;
import com.meituan.octo.portal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProviderServiceImpl implements ProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderServiceImpl.class);
    private static final String ZK_ROOT_PATH = "/octo/nameservice";

    @Autowired
    private ZookeeperClient zkClient;

    @Override
    public List<ProviderDesc> getProviders(int env, String serviceName) {
        String envStr = ZkPathUtil.toEnvStr(env);
        String providerPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider";
        List<ProviderDesc> providerDescList = new ArrayList<>();
        try {
            List<String> providerNodes = zkClient.getChildren(providerPath);
            for (String node : providerNodes) {
                String nodeData = zkClient.getData(providerPath + "/" + node);
                ProviderDesc providerDesc = JsonUtil.parseJson(nodeData, ProviderDesc.class);
                providerDescList.add(providerDesc);
            }
            return providerDescList;
        } catch (Exception e) {
            LOGGER.error("get providers failed: {}", e);
        }
        return providerDescList;
    }

    @Override
    public String updateProviderStatus(int env, String serviceName, String ip, int port, int newStatus) {
        String envStr = ZkPathUtil.toEnvStr(env);
        String providerPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider";
        String providerDescPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider" + "/" + ip + ":" + port;
        try {
            long curTime = System.currentTimeMillis() / 1000;

            String parentData = zkClient.getData(providerPath);
            ProviderNodeData providerNodeData = JsonUtil.parseJson(parentData, ProviderNodeData.class);
            if (providerNodeData != null) {
                providerNodeData.setLastUpdateTime(curTime);
                zkClient.setData(providerPath, JsonUtil.toString(providerNodeData));
            }

            String nodeData = zkClient.getData(providerDescPath);
            ProviderDesc providerDesc = JsonUtil.parseJson(nodeData, ProviderDesc.class);

            providerDesc.setStatus(newStatus);
            providerDesc.setLastUpdateTime(curTime);
            zkClient.setData(providerDescPath, JsonUtil.toString(providerDesc));
            return JsonUtil.createSuccess(null);
        } catch (Exception e) {
            LOGGER.error("update provider status failed: {}", e);
            return JsonUtil.createFail("update provider status failed.");
        }
    }

    @Override
    public String updateProviderWeight(int env, String serviceName, String ip, int port, double fweight) {
        String envStr = ZkPathUtil.toEnvStr(env);
        String providerPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider";
        String providerDescPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider" + "/" + ip + ":" + port;
        try {
            long curTime = System.currentTimeMillis() / 1000;

            String parentData = zkClient.getData(providerPath);
            ProviderNodeData providerNodeData = JsonUtil.parseJson(parentData, ProviderNodeData.class);
            if (providerNodeData != null) {
                providerNodeData.setLastUpdateTime(curTime);
                zkClient.setData(providerPath, JsonUtil.toString(providerNodeData));
            }

            String nodeData = zkClient.getData(providerDescPath);
            ProviderDesc providerDesc = JsonUtil.parseJson(nodeData, ProviderDesc.class);
            providerDesc.setFweight(fweight);
            providerDesc.setWeight(new Double(fweight).intValue());
            providerDesc.setLastUpdateTime(curTime);
            zkClient.setData(providerDescPath, JsonUtil.toString(providerDesc));

            return JsonUtil.createSuccess(null);
        } catch (Exception e) {
            LOGGER.error("update provider weight failed: {}", e);
            return JsonUtil.createFail("update provider weight failed.");
        }
    }

    @Override
    public String deleteProvider(int env, String serviceName, String ip, int port) {
        String envStr = ZkPathUtil.toEnvStr(env);
        String providerPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider";
        String providerDescPath = ZK_ROOT_PATH + "/" + envStr + "/" + serviceName + "/provider" + "/" + ip + ":" + port;
        try {
            long curTime = System.currentTimeMillis() / 1000;

            String parentData = zkClient.getData(providerPath);
            ProviderNodeData providerNodeData = JsonUtil.parseJson(parentData, ProviderNodeData.class);
            if (providerNodeData != null) {
                providerNodeData.setLastUpdateTime(curTime);
                zkClient.setData(providerPath, JsonUtil.toString(providerNodeData));
            }

            zkClient.delete(providerDescPath);

            return JsonUtil.createSuccess(null);
        } catch (Exception e) {
            LOGGER.error("delete provider {} failed: {}", ip + ":" + port, e);
            return JsonUtil.createFail("delete provider failed.");
        }
    }
}
