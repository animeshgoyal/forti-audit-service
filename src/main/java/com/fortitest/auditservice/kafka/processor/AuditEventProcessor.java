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
package com.fortitest.auditservice.kafka.processor;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.service.AuditService;
import io.micrometer.core.annotation.Timed;
import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditEventProcessor<KIn, KOut> implements Processor<KIn, AuditEvent, KOut, AuditEvent> {

    private static final Logger LOG = LogManager.getLogger(AuditEventProcessor.class);

    private ProcessorContext <KOut, AuditEvent> context;
    private final AuditService auditService;
    public AuditEventProcessor(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void init(final ProcessorContext<KOut, AuditEvent> context) {
        this.context = context;
    }

    @Override
    @Timed(value = "audit.logs.save", description = "Time taken to process audit logs")
    public void process(Record<KIn, AuditEvent> record) {
        // the keys should never be null
        if (record.key() == null) {
            throw new StreamsException("Record key is null!");
        }
        LOG.trace("Received event {} for processing", record.value());
        auditService.logAudit(record.value());
        context.forward(new Record<>((KOut)record.key(), record.value(), record.timestamp()));
    }

    @Override
    public void close() {
    }
}
