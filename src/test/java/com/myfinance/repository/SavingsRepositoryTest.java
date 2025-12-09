package com.myfinance.repository;

import com.myfinance.domain.Category;
import com.myfinance.domain.Savings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SavingsRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    private Category majorCategory;
    private Category minorCategory;

    @BeforeEach
    void setUp() {
        // 테스트용 카테고리 생성 및 저장
        majorCategory = Category.of(savingsType,"테스트1", null);
        categoryRepository.save(majorCategory);

        minorCategory = Category.of(savingsType,"테스트2", majorCategory);
        categoryRepository.save(minorCategory);
    }

    @Test
    @DisplayName("저축 저장 및 조회")
    void saveSavings_Success(){

        // given
        Savings savings = Savings.of(LocalDate.now(), majorCategory, minorCategory, "0000000000000", "테스트", new BigDecimal(100000));

        // when
        Savings savingData = savingsRepository.save(savings);

        //then
        assertThat(savings.getId()).isNotNull();
        assertThat(savings.getId()).isEqualTo(savingData.getId());

    }

    @Test
    @DisplayName("ID로 저축 조회")
    void findSavingsById_Success(){

    }

    @Test
    @DisplayName("월별 저축 목록 조회")
    void findMonthlySavings_Success(){

    }

    @Test
    @DisplayName("저축 삭제")
    void deleteSavings_Success(){

    }
}
