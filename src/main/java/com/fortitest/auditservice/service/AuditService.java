/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.fortitest.auditservice.service;

import com.fortitest.auditservice.event.AuditEvent;

import java.util.List;

public interface AuditService {
    String logAudit(AuditEvent auditEvent);
    List<AuditEvent> getAuditEvents(int pageNumber, int pageSize, String sort);

}
