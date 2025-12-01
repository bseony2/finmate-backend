package com.myfinance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Savings extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate savingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private Category majorCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id")
    private Category minorCategory;

    @Column(length = 20)
    private String acctNo;

    @Column(length = 30)
    private String content;

    @Column(nullable = false)
    private BigDecimal amount;

    public static Savings of(LocalDate savingDate, Category majorCategory, Category minorCategory, String acctNo, String content, BigDecimal amount) {

        Savings savings = new Savings();
        savings.savingDate = savingDate;
        savings.majorCategory = majorCategory;
        savings.minorCategory = minorCategory;
        savings.content = content;
        savings.acctNo = acctNo;
        savings.amount = amount;

        return savings;
    }
}