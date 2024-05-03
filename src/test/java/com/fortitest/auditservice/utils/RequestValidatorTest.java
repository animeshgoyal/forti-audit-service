package com.fortitest.auditservice.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestValidatorTest {

    @Test
    public void testValidateRequest() {
        assertTrue(RequestValidator.validate(0, 10, "timeStamp"));
        assertFalse(RequestValidator.validate(-1, 10, "timeStamp"));
        assertFalse(RequestValidator.validate(-1, 0, "timeStamp"));
        assertFalse(RequestValidator.validate(0, -1, "timeStamp"));
        assertFalse(RequestValidator.validate(0, 1001, "timeStamp"));
        assertFalse(RequestValidator.validate(0, 1000, "random"));
    }
}