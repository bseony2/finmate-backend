package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.CategoryType;
import com.myfinance.dto.request.SavingsRequest;
import com.myfinance.dto.response.SavingsResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.CategoryTypeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
@SpringBootTest
class SavingsServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryTypeRepository categoryTypeRepository;
    @Autowired
    private SavingsService savingsService;

    private CategoryType categoryType;
    private Category majorCategory;
    private Category minorCategory;

    @BeforeEach
    void setUp() {
        categoryType = CategoryType.of("테스트");
        categoryTypeRepository.save(categoryType);
        majorCategory = createCategory("테스트1", null);
        minorCategory = createCategory("테스트2", majorCategory);
    }


    private Category createCategory(String name, Category parent) {

        Category category;
        if (parent == null) {
            category = Category.createTopLevelCategory(categoryType, name);
        } else {
            category = Category.createSubCategory(categoryType, name, parent);
        }

        categoryRepository.save(category);

        return category;
    }

    @Test
    @DisplayName("저축 신규")
    void Create_Savings_Success() {
        // given
        SavingsRequest request = createRequest();

        // when
        SavingsResponse response = savingsService.createSavings(request);

        // then
        assertThat(response.getContent()).isEqualTo("테스트 저축");

    }

    @Test
    @DisplayName("저축 조회 실패 - 존재하지 않는 ID")
    void getSavingsById_NotFound() {
        // given
        long notExistId = 9999999999L;

        // when & then
        assertThatThrownBy(() -> savingsService.getSavingsById(notExistId))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("존재하지 않는 저축입니다");

    }

    @Test
    @DisplayName("월별 저축 조회 - 날짜 범위 검색")
    void getMonthlySavings_Success() {
        // given
        savingsService.createSavings(createRequest(LocalDate.of(1000, 12, 1)));
        savingsService.createSavings(createRequest(LocalDate.of(1000, 12, 25)));
        savingsService.createSavings(createRequest(LocalDate.of(1000, 11, 1)));

        // when
        List<SavingsResponse> monthlySavings = savingsService.getMonthlySavings(1000, 12);

        // then
        assertThat(monthlySavings).hasSize(2);
        assertThat(monthlySavings)
                .extracting(SavingsResponse::getSavingDate)
                .allMatch(date -> 1000 ==date.getYear() && 12 == date.getMonthValue());
    }

    @Test
    @DisplayName("저축 수정 성공")
    void updateSavings_Success() {
        // given
        SavingsRequest request = this.createRequest();
        SavingsResponse savings = savingsService.createSavings(request);

        SavingsRequest updateRequest = createUpdateRequest(savings.getId());

        // when
        SavingsResponse updated = savingsService.updateSavings(updateRequest);

        // then
        assertThat(updated.getId()).isEqualTo(savings.getId());
        assertThat(updated.getSavingDate()).isEqualTo(LocalDate.of(2025, 12, 1));
        assertThat(updated.getMajorCategoryName()).isEqualTo("update1");
        assertThat(updated.getMinorCategoryName()).isEqualTo("update2");
        assertThat(updated.getAcctNo()).isEqualTo("업데이트");
        assertThat(updated.getContent()).isEqualTo("업데이트");
        assertThat(updated.getAmount()).isEqualTo(new BigDecimal(999999));
    }

    @Test
    @DisplayName("저축 수정 실패 - 존재하지 않는 ID")
    void updateSavings_NotFound() {
        // given
        SavingsRequest updateRequest = createUpdateRequest(999999999L);

        // when & then
        assertThatThrownBy(() -> savingsService.updateSavings(updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 저축입니다");

    }

    private SavingsRequest createUpdateRequest(Long id) {
        Category update1 = createCategory("update1", null);
        Category update2 = createCategory("update2", update1);

        return this.createRequest(
                id,
                LocalDate.of(2025, 12, 1)
                , update1
                , update2
                , "업데이트"
                , "업데이트"
                , new BigDecimal(999999)
        );
    }

    @Test
    @DisplayName("저축 삭제 성공")
    void deleteSavings_Success() {
        // given
        SavingsResponse savings = savingsService.createSavings(createRequest());

        // when
        savingsService.deleteSavings(savings.getId());

        // then
        assertThatThrownBy(() -> savingsService.getSavingsById(savings.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 저축입니다");
    }

    @Test
    @DisplayName("저축 삭제 실패 - 존재하지 않는 ID")
    void deleteSavings_NotFound() {
        // given
        Long id = 99999999L;
        
        // when & then
        assertThatThrownBy(() -> savingsService.deleteSavings(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 저축입니다");
        
    }

    private SavingsRequest createRequest(LocalDate localdate) {
        return SavingsRequest.builder()
                .savingDate(localdate)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(minorCategory.getId())
                .acctNo("1234567890")
                .content("테스트 저축")
                .amount(new BigDecimal(100000))
                .build();
    }

    private SavingsRequest createRequest() {
        return SavingsRequest.builder()
                .savingDate(LocalDate.now())
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(minorCategory.getId())
                .acctNo("1234567890")
                .content("테스트 저축")
                .amount(new BigDecimal(100000))
                .build();
    }

    private SavingsRequest createRequest(Long id,LocalDate localdate, Category majorCategory, Category minorCategory, String accString, String content, BigDecimal amount) {
        return SavingsRequest.builder()
                .id(id)
                .savingDate(localdate)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(minorCategory.getId())
                .acctNo(accString)
                .content(content)
                .amount(amount)
                .build();
    }
}
