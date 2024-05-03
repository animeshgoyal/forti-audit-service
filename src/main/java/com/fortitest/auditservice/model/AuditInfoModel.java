package com.fortitest.auditservice.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "audit_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditInfoModel {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;
    @Column(name = "source_obj_id", nullable = false)
    private String sourceObjId;
    @Column(name = "source_obj_name", nullable = false)
    private String sourceObjName;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "action_type", nullable = false)
    private String actionType;
    @Column(name = "time_stamp", nullable = false)
    private Date timeStamp;
    @Column(name = "service", nullable = true)
    private String service;
    @Column(name = "instance_id", nullable = true)
    private String instanceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSourceObjId() {
        return sourceObjId;
    }

    public void setSourceObjId(String sourceObjId) {
        this.sourceObjId = sourceObjId;
    }

    public String getSourceObjName() {
        return sourceObjName;
    }

    public void setSourceObjName(String sourceObjName) {
        this.sourceObjName = sourceObjName;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}