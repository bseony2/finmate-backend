package com.finmate.ledger.domain.account;

import lombok.Getter;

/**
 * 계좌 상태
 */
@Getter
public enum AccountStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }
}