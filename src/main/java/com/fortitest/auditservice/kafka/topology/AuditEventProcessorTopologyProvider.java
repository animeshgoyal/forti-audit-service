/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fortitest.auditservice.kafka.topology;

import com.fortitest.auditservice.kafka.TopologyProvider;
import com.fortitest.auditservice.kafka.processor.AuditEventProcessor;
import com.fortitest.auditservice.service.AuditService;
import org.apache.kafka.streams.Topology;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditEventProcessorTopologyProvider implements TopologyProvider {
    private static final Logger LOG = LogManager.getLogger(AuditEventProcessorTopologyProvider.class);

    private static final String PROCESSOR_SAVE_AUDIT_EVENTS = "processor-save-audit-events";
    private static final String PROCESSOR_SOURCE = "processor-source";
    private final String sourceTopic;
    private final AuditService auditService;

    public AuditEventProcessorTopologyProvider(String sourceTopic,
                                               AuditService auditService) {
        this.sourceTopic = sourceTopic;
        this.auditService = auditService;
    }

    @Override
    public Topology get() {
        LOG.info("Creating Stream Topology");
        final Topology topology = new Topology();

        topology
                .addSource(Topology.AutoOffsetReset.EARLIEST, PROCESSOR_SOURCE, sourceTopic)
                .addProcessor(PROCESSOR_SAVE_AUDIT_EVENTS,
                        () -> new AuditEventProcessor<>(auditService),
                        PROCESSOR_SOURCE);

        return topology;
    }
}
