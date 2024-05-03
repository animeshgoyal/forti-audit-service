package com.fortitest.auditservice.utils;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class RequestValidator {

    private static int MAX_PAGE_SIZE = 1000;
    private static List<String> validSortFields = new ArrayList<>();
    static {
        validSortFields.add("timeStamp");
        validSortFields.add("actionType");
        validSortFields.add("service");
    }
    public static boolean validate(int page, int size,
                         String sort) {
        if(page < 0) {
            return false;
        }

        if(size < 0 || size > MAX_PAGE_SIZE) {
            return false;
        }

        return validSortFields.contains(sort);
    }
}
