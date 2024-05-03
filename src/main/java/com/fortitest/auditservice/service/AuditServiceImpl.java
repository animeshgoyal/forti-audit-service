/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.fortitest.auditservice.service;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.model.AuditInfoModel;
import com.fortitest.auditservice.repository.AuditRepository;
import com.fortitest.auditservice.utils.ModelConverter;
import com.fortitest.auditservice.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public String logAudit(AuditEvent auditEvent) {
        log.debug("Logging Audit Record: {}", auditEvent);
        AuditInfoModel auditInfoModel = auditRepository.save(ModelConverter.toModel(auditEvent));
        return auditInfoModel.getId();
    }

    @Override
    public List<AuditEvent> getAuditEvents(int page, int size, String sort) {
        log.debug("Fetching Audit Records for page {}, size {} and sort param {}", page, size, sort);
        boolean isAdmin = SecurityUtils.isAdmin();
        log.debug("Is executing the fetch for admin {}",isAdmin);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));
        Page<AuditInfoModel> auditInfoModels = isAdmin ? auditRepository.findAll(pageRequest) :
                auditRepository.findByUserId(SecurityUtils.getUser(), pageRequest);
        return ModelConverter.toEvents(auditInfoModels);
    }

}
