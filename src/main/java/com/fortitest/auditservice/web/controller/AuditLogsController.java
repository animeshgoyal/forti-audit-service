package com.fortitest.auditservice.web.controller;

import com.fortitest.auditservice.event.AuditEvent;
import com.fortitest.auditservice.service.AuditService;
import com.fortitest.auditservice.utils.RequestValidator;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/audit-logs")
public class AuditLogsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AuditService auditService;

    @Autowired
    public AuditLogsController(final AuditService auditService) {
        this.auditService = auditService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Timed(value = "audit.logs.api.get", description = "Time taken to return audit logs")
    public ResponseEntity<List<AuditEvent>> getAuditInfo(@RequestParam int page, @RequestParam int size,
                                     @RequestParam String sort) {
        if(RequestValidator.validate(page, size, sort)) {
            List<AuditEvent> auditEvents = auditService.getAuditEvents(page, size, sort);
            LOGGER.debug("Found {} records for the User {}", auditEvents.size(), "");
            return !auditEvents.isEmpty() ? ResponseEntity.ok(auditEvents) : new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
        } else {
            return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
        }
    }

}
