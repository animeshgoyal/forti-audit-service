package com.fortitest.auditservice.test.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.model.AuditInfoModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ObjectCreator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AuditEvent getEvent() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("audit-event.json");
        return objectMapper.readValue(is, AuditEvent.class);
    }

    public static List<AuditEvent> getEvents() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("audit-events.json");
        return objectMapper.readValue(is, new TypeReference<List<AuditEvent>>(){});
    }

    public static AuditInfoModel getInfoModel() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("audit-info-model.json");
        return objectMapper.readValue(is, AuditInfoModel.class);
    }
}
