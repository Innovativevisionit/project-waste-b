package com.sql.authentication.Enum;

import lombok.Getter;

@Getter
public enum StatusEnum {
    pending("Pending"),
    completed("Completed"),
    rejected("Rejected"),
    reject("Reject"),
    approve("Approve"),
    approved("Approved"),
    withdrawn("Withdrawn");
    private final String value;
    StatusEnum(String value) {
        this.value = value;
    }
}
