/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.fortitest.auditservice.service;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.model.AuditInfoModel;
import com.fortitest.auditservice.repository.AuditRepository;
import com.fortitest.auditservice.test.utils.ObjectCreator;
import com.fortitest.auditservice.test.utils.SecurityTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuditServiceImplUTest {
    @Autowired
    AuditService auditService;

    @Test
    public void testSave() throws IOException {
        SecurityTestUtils.setupAdmin();
        AuditEvent auditEvent = ObjectCreator.getEvent();
        String id = auditService.logAudit(auditEvent);
        List<AuditEvent> auditEventList = auditService.getAuditEvents(0, 1, "timeStamp");
        assertEquals(1, auditEventList.size());
        AuditEvent auditEventResult = auditEventList.get(0);
        assertEquals(auditEvent.getSourceObjectName(), auditEventResult.getSourceObjectName());
        assertEquals(auditEvent.getSourceObjId(), auditEventResult.getSourceObjId());
        assertNotNull(id);
    }

    @Test
    public void testAuditLog() throws IOException {
        AuditInfoModel auditInfoModel = ObjectCreator.getInfoModel();
        AuditEvent auditEvent = ObjectCreator.getEvent();
        String id = auditService.logAudit(auditEvent);
        assertNotNull(id);
    }
}