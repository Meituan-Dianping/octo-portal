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

package com.meituan.octo.portal.controller;

import com.meituan.octo.portal.model.ProviderDesc;
import com.meituan.octo.portal.model.param.ProviderInfoParam;
import com.meituan.octo.portal.model.param.ProviderStatusParam;
import com.meituan.octo.portal.model.param.ProviderWeightParam;
import com.meituan.octo.portal.service.ProviderService;
import com.meituan.octo.portal.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    ProviderService providerService;

    /**
     * 获取服务下所有节点信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getProviders(@RequestParam(value = "env") int env,
                               @RequestParam(value = "serviceName") String serviceName) {
        List<ProviderDesc> providerDescList = providerService.getProviders(env, serviceName);
        return JsonUtil.createSuccess(providerDescList);
    }

    /**
     * 调整权重
     */
    @RequestMapping(value = "/weight", method = RequestMethod.PUT)
    public String updateProviderWeight(@RequestBody ProviderWeightParam providerWeightParam) {
        int env = providerWeightParam.getEnv();
        String serviceName = providerWeightParam.getServiceName();
        String ip = providerWeightParam.getIp();
        int port = providerWeightParam.getPort();
        double fweight = providerWeightParam.getWeight();

        return providerService.updateProviderWeight(env, serviceName, ip, port, fweight);
    }

    /**
     * 启用禁用状态调整
     */
    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    public String updateProviderStatus(@RequestBody ProviderStatusParam provierStatusParam) {
        int env = provierStatusParam.getEnv();
        String serviceName = provierStatusParam.getServiceName();
        String ip = provierStatusParam.getIp();
        int port = provierStatusParam.getPort();
        int newStatus = provierStatusParam.getStatus();

        return providerService.updateProviderStatus(env, serviceName, ip, port, newStatus);
    }

    /**
     * 删除节点
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String deleteProvider(@RequestBody ProviderInfoParam providerInfoParam) {
        int env = providerInfoParam.getEnv();
        String serviceName = providerInfoParam.getServiceName();
        String ip = providerInfoParam.getIp();
        int port = providerInfoParam.getPort();

        return providerService.deleteProvider(env, serviceName, ip, port);
    }
}
