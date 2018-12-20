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

package com.meituan.octo.portal.service.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZookeeperClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClient.class);

    private static CuratorFramework client;

    private String address;

    public ZookeeperClient(@Value("${zk.address}") String address) {
        this.address = address;
        try {
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.builder()
                    .connectString(address)
                    .retryPolicy(retryPolicy)
                    .connectionTimeoutMs(5 * 1000)
                    .sessionTimeoutMs(5 * 1000)
                    .build();
            client.start();
        } catch (RuntimeException e) {
            LOGGER.error("ZookeeperClient[{}] init failed.", address, e);
        }
    }

    public boolean isExist(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] isExist failed, path={}", address, path, e);
        }
        return false;
    }


    public String getData(String path) throws Exception {
        try {
            byte[] data = client.getData().forPath(path);
            return data == null ? "" : new String(data, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] getNodeData: {} failed.", address, path, e);
            throw e;
        }
    }

    public void setData(String path, String data) throws Exception {
        if (!isExist(path)) {
            return;
        }
        try {
            byte[] bytes = data == null ? new byte[0] : data.getBytes("UTF-8");
            Stat stat = client.checkExists().forPath(path);
            client.setData().withVersion(stat.getVersion()).forPath(path, bytes);
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] setData:{} failed.", address, path, e);
            throw e;
        }
    }

    public void create(String path) throws Exception {
        if (isExist(path)) {
            return;
        }
        try {
            client.create().creatingParentsIfNeeded().forPath(path);
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] createWithParent:{} failed.", address, path, e);
            throw e;
        }
    }

    public void createWithData(String path, String data) throws Exception {
        if (isExist(path)) {
            return;
        }
        try {
            byte[] bytes = data == null ? new byte[0] : data.getBytes("UTF-8");
            client.create().creatingParentsIfNeeded().forPath(path, bytes);
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] createWithParent:{} failed.", address, path, e);
            throw e;
        }
    }

    public void delete(String path) throws Exception {
        if (!isExist(path)) {
            return;
        }
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] deletePath:{} failed.", address, path, e);
            throw e;
        }
    }

    public List<String> getChildren(String parentPath) throws Exception {
        try {
            return client.getChildren().forPath(parentPath);
        } catch (Exception e) {
            LOGGER.error("ZookeeperClient[{}] getChildren failed, parentPath:{}.", address, parentPath, e);
            throw e;
        }
    }
}
