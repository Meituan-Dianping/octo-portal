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

package com.meituan.octo.portal.model;

import java.util.Map;

public class ProviderDesc {
    private String appkey;
    private String version;
    private String ip;
    private int port;
    private int weight;
    private int status;
    private int env;
    private int warmup;
    private long lastUpdateTime;
    private double fweight;
    private int heartbeatSupport;
    private String protocol;
    private int role;
    private int serverType;
    private Map<String, ServiceDetail> serviceInfo;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public int getWarmup() {
        return warmup;
    }

    public void setWarmup(int warmup) {
        this.warmup = warmup;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getFweight() {
        return fweight;
    }

    public void setFweight(double fweight) {
        this.fweight = fweight;
    }

    public int getHeartbeatSupport() {
        return heartbeatSupport;
    }

    public void setHeartbeatSupport(int heartbeatSupport) {
        this.heartbeatSupport = heartbeatSupport;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public Map<String, ServiceDetail> getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(Map<String, ServiceDetail> serviceInfo) {
        this.serviceInfo = serviceInfo;
    }
}
