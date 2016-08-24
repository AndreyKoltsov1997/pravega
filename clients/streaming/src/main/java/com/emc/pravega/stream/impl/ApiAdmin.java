/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.emc.pravega.stream.impl;

import com.emc.pravega.stream.Api;
import com.emc.pravega.controller.stream.api.v1.AdminService;
import com.emc.pravega.controller.stream.api.v1.Status;
import com.emc.pravega.stream.StreamConfiguration;
import com.emc.pravega.stream.impl.model.ModelHelper;
import org.apache.thrift.TException;

import java.util.concurrent.CompletableFuture;

/**
 * RPC based implementation of Stream Controller Admin V1 API
 */
public class ApiAdmin implements Api.Admin {
    @Override
    public CompletableFuture<Status> createStream(StreamConfiguration streamConfig) {
        //Use RPC client to invoke createStream
        AdminService.Client client = new AdminService.Client(null);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return client.createStream(ModelHelper.decode(streamConfig));
            } catch (TException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Status> alterStream(StreamConfiguration streamConfig) {
        //Use RPC client to invoke alterStream
        AdminService.Client client = new AdminService.Client(null);
        try {
            client.alterStream(null);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }
}
