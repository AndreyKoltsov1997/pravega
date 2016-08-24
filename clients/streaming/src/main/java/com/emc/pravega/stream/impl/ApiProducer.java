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
import com.emc.pravega.controller.stream.api.v1.ProducerService;
import com.emc.pravega.controller.stream.api.v1.SegmentId;
import com.emc.pravega.stream.StreamSegments;
import com.emc.pravega.stream.impl.model.ModelHelper;
import org.apache.thrift.TException;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * RPC based implementation of Stream Controller Producer V1 API
 */
public class ApiProducer implements Api.Producer {

    @Override
    public CompletableFuture<StreamSegments> getCurrentSegments(String stream) {
        ProducerService.Client client = new ProducerService.Client(null);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new StreamSegments(client.getCurrentSegments(stream).
                        parallelStream().map(ModelHelper::encode).collect(Collectors.toList()),
                        System.currentTimeMillis());
            } catch (TException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<URI> getURI(SegmentId id) {
        //Use RPC client to invoke getURI
        ProducerService.Client client = new ProducerService.Client(null);
        try {
            client.getURI(null);
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }
}