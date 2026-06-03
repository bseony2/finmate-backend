package com.finmate.ledger.application;

import com.finmate.ledger.application.savings.dto.SavingsCommand;
import com.finmate.ledger.application.savings.dto.SavingsResult;
import com.finmate.ledger.domain.category.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
class SavingsServiceTest extends AbstractServiceTest{

    @BeforeEach
    void setUp() {

        String type = "테스트";
        String major = "테스트1";
        String minor = "테스트2";

        setInitCategory(type, major, minor);

    }

    @Test
    @DisplayName("저축 신규")
    void Create_Savings_Success() {
        // given
        SavingsCommand command = createRequest();

        // when
        SavingsResult result = savingsService.createSavings(command);

        // then
        assertThat(result.getContent()).isEqualTo("테스트 저축");

    }

    @Test
    @DisplayName("저축 조회 실패 - 존재하지 않는 ID")
    void getSavingsById_NotFound() {
        // given
        long notExistId = -1;

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
        List<SavingsResult> results = savingsService.getMonthlySavings(1000, 12);

        // then
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(SavingsResult::getSavingDate)
                .allMatch(date -> 1000 ==date.getYear() && 12 == date.getMonthValue());
    }

    @Test
    @DisplayName("저축 수정 성공")
    void updateSavings_Success() {
        // given
        SavingsCommand command = this.createRequest();
        SavingsResult result = savingsService.createSavings(command);

        SavingsCommand updateRequest = createUpdateRequest();

        // when
        SavingsResult updated = savingsService.updateSavings(result.getId(), updateRequest);

        // then
        assertThat(updated.getId()).isEqualTo(result.getId());
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
        Long notExistId = 999999999L;
        SavingsCommand updateRequest = createRequest();

        // when & then
        assertThatThrownBy(() -> savingsService.updateSavings(notExistId, updateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 저축입니다");

    }

    private SavingsCommand createUpdateRequest() {
        Category update1 = createCategory("update1", null);
        Category update2 = createCategory("update2", update1);

        return this.createRequest(
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
        SavingsResult savings = savingsService.createSavings(createRequest());

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
        Long id = -1L;
        
        // when & then
        assertThatThrownBy(() -> savingsService.deleteSavings(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 저축입니다");
        
    }

    private SavingsCommand createRequest(LocalDate localdate) {
        return createRequest(
                localdate,
                majorCategory,
                minorCategory,
                "1234567890",
                "테스트 저축",
                new BigDecimal(100000)
        );
    }

    private SavingsCommand createRequest() {
        return new SavingsCommand(
                LocalDate.now(),
                majorCategory.getId(),
                minorCategory.getId(),
                "1234567890",
                "테스트 저축",
                new BigDecimal(100000)
        );
    }

    private SavingsCommand createRequest(LocalDate localdate, Category majorCategory, Category minorCategory, String acctNo, String content, BigDecimal amount) {
        return new SavingsCommand(
                localdate,
                majorCategory.getId(),
                minorCategory.getId(),
                acctNo,
                content,
                amount
        );
    }
}
