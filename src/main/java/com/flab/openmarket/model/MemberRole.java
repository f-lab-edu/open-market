package com.flab.openmarket.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberRole {
    CUSTOMER("customer", "구매자"),
    SELLER("seller", "판매자");

    private final String value;
    private final String title;

    MemberRole(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public static MemberRole fromValue(String value) {
        return Arrays.stream(MemberRole.values())
                .filter(memberRole -> memberRole.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("convert to enum exception"));
    }
}
