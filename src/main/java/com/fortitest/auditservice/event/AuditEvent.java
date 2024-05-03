package com.fortitest.auditservice.event;

import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEvent {
    private String sourceObjId;
    private String sourceObjectName;
    private String userId;
    private String actionType;
    private Date timeStamp;
    private String service;
    private String instanceId;
}
