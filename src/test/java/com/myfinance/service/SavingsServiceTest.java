package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.CategoryType;
import com.myfinance.dto.request.SavingsRequest;
import com.myfinance.dto.response.SavingsResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.CategoryTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
@SpringBootTest
@RequiredArgsConstructor
class SavingsServiceTest {

    private final CategoryRepository categoryRepository;
    private final CategoryTypeRepository categoryTypeRepository;
    private final SavingsService savingsService;

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
        savingsService.createSavings(createRequest(LocalDate.of(2025, 12, 1)));
        savingsService.createSavings(createRequest(LocalDate.of(2025, 12, 3)));
        savingsService.createSavings(createRequest(LocalDate.of(2025, 11, 1)));

        // when
        List<SavingsResponse> monthlySavings = savingsService.getMonthlySavings(2025, 12);

        // then
        assertThat(monthlySavings).hasSize(2);
        assertThat(monthlySavings)
                .extracting(SavingsResponse::getSavingDate)
                .allMatch(date -> 2025 ==date.getYear() && 12 == date.getMonthValue());
    }

    @Test
    @Disabled("TODO: updateSavings() 메서드 먼저 구현 필요")
    @DisplayName("저축 수정 성공")
    void updateSavings_Success() {
        // TODO: 구현 필요
    }

    @Test
    @Disabled("TODO: updateSavings() 메서드 먼저 구현 필요")
    @DisplayName("저축 수정 실패 - 존재하지 않는 ID")
    void updateSavings_NotFound() {
        // TODO: 구현 필요
    }

    @Test
    @Disabled("TODO: deleteSavings() 메서드 먼저 구현 필요")
    @DisplayName("저축 삭제 성공")
    void deleteSavings_Success() {
        // TODO: 구현 필요
    }

    @Test
    @Disabled("TODO: deleteSavings() 메서드 먼저 구현 필요")
    @DisplayName("저축 삭제 실패 - 존재하지 않는 ID")
    void deleteSavings_NotFound() {
        // TODO: 구현 필요
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
}
