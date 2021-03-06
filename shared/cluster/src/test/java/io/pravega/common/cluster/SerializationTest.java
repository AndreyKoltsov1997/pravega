/**
 * Copyright (c) Dell Inc., or its subsidiaries. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package io.pravega.common.cluster;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SerializationTest {
    @Test
    public void hostTest() {
        Host host = new Host("1.1.1.1", 1234, "ep");
        byte[] serialized = host.toBytes();
        assertEquals(host, Host.fromBytes(serialized));
    }
    
    @Test
    public void containersetTest() throws IOException {
        ContainerSet set = new ContainerSet(Sets.newSet(1, 2, 3, 4));
        byte[] serialized = ContainerSet.SERIALIZER.serialize(set).getCopy();
        assertEquals(set.getContainerSet(), ContainerSet.SERIALIZER.deserialize(serialized).getContainerSet());
    }
    
    @Test
    public void hostContainerMapTest() {
        Host host = new Host("1.1.1.1", 1234, "ep");
        Host host2 = new Host("1.1.1.2", 1234, "ep");
        ContainerSet set = new ContainerSet(Sets.newSet(1, 2, 3, 4));
        ContainerSet set2 = new ContainerSet(Sets.newSet(1, 2, 3, 4));
        Map<Host, ContainerSet> map = new HashMap<>();
        map.put(host, set);
        map.put(host2, set2);
        HostContainerMap hostContainerMap = new HostContainerMap(map);
        byte[] serialized = hostContainerMap.toBytes();
        HostContainerMap deserialized = HostContainerMap.fromBytes(serialized);
        assertTrue(Maps.difference(hostContainerMap.getHostContainerMap(), deserialized.getHostContainerMap()).areEqual());
    }
}
