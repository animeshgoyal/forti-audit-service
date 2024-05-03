package com.fortitest.auditservice.utils;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.model.AuditInfoModel;
import org.springframework.data.domain.Page;

import java.util.List;

public class ModelConverter {

    public static AuditInfoModel toModel(AuditEvent auditEvent) {
        return AuditInfoModel.builder().sourceObjName(auditEvent.getSourceObjectName())
                .sourceObjId(auditEvent.getSourceObjId())
                .userId(auditEvent.getUserId())
                .actionType(auditEvent.getActionType())
                .timeStamp(auditEvent.getTimeStamp())
                .service(auditEvent.getService())
                .instanceId(auditEvent.getInstanceId()).build();
    }

    public static AuditEvent toEvent(AuditInfoModel auditInfoModel) {
        return AuditEvent.builder().sourceObjectName(auditInfoModel.getSourceObjName())
                .sourceObjId(auditInfoModel.getSourceObjId())
                .userId(auditInfoModel.getUserId())
                .actionType(auditInfoModel.getActionType())
                .timeStamp(auditInfoModel.getTimeStamp())
                .service(auditInfoModel.getService())
                .instanceId(auditInfoModel.getInstanceId()).build();
    }

    public static List<AuditEvent> toEvents(Page<AuditInfoModel> auditInfoModels) {
        return auditInfoModels.stream().map(ModelConverter::toEvent).toList();
    }
}
