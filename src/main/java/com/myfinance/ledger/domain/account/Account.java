package com.myfinance.ledger.domain.account;

import com.myfinance.ledger.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기관코드
    @Column(name = "INST_CD", nullable = false, length = 5)
    private String instCd;

    // 계좌번호
    @Column(name = "ACCT_NO", nullable = false, length = 20)
    private String acctNo;

    // 계좌 별명
    @Column(name = "ACCT_ALIAS", length = 15)
    private String acctAlias;

    // 계좌 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS", nullable = false, length = 10)
    private AccountStatus status;

    public static Account of(String instCd,  String acctNo) {
        validateInstCd(instCd);
        validateAcctNo(acctNo);
        
        Account account = new Account();
        account.instCd = instCd;
        account.acctNo = acctNo;

        return account;
    }

    private static void validateInstCd(String instCd) {
        Assert.hasText(instCd, "기관코드는 필수입니다.");
        Assert.isTrue(3 <= instCd.length() && instCd.length() <= 5, "기관코드는 3자리 또는 5자리여야 합니다.");
    }

    private static void validateAcctNo(String acctNo) {
        Assert.hasText(acctNo, "계좌번호는 필수입니다");
        Assert.isTrue(10 <= acctNo.length() && acctNo.length() <= 20, "계좌번호는 10 ~ 20 자리여야 합니다.");
        Assert.isTrue(acctNo.matches("^[0-9]+$"), "계좌번호는 숫자만 입력 가능합니다.");
    }

    public Account withAlias(String acctAlias) {
        this.acctAlias = acctAlias;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account account)) return false;
        return Objects.equals(instCd, account.instCd) && Objects.equals(acctNo, account.acctNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instCd, acctNo);
    }
}
