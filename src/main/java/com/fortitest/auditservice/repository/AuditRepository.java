/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved. 
 * |-------------------------------------------------
 */
package com.fortitest.auditservice.repository;

import com.fortitest.auditservice.model.AuditInfoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuditRepository extends JpaRepository<AuditInfoModel,String> {
    Page<AuditInfoModel> findByUserId(String userId, Pageable pageable);
    @Query("SELECT a FROM AuditInfoModel a WHERE a.userId = ?1")
    List<AuditInfoModel> findByUserIdNonPage(String userId);
}
