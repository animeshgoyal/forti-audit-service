package com.fortitest.auditservice.kafka.serdes;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.kafka.serdes.json.JsonDeserializer;
import com.fortitest.auditservice.kafka.serdes.json.JsonSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomAuditEventSerde implements Serde<AuditEvent> {
    private final JsonSerializer<AuditEvent> serializer = new JsonSerializer<>();
    private final JsonDeserializer<AuditEvent> deserializer = new JsonDeserializer<>(AuditEvent.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        serializer.configure(configs, isKey);
        deserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        serializer.close();
        deserializer.close();
    }

    @Override
    public Serializer<AuditEvent> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<AuditEvent> deserializer() {
        return deserializer;
    }
}