DROP TABLE EXPENSE;
-- expense 테이블 생성
CREATE TABLE expense (
    id BIGSERIAL PRIMARY KEY,

    -- 지출 기본 정보
    expense_date DATE NOT NULL,
    expense_type INTEGER NOT NULL,  -- 0: 고정지출, 1: 변동지출

    -- 카테고리 (외래키)
    major_category_id BIGINT NOT NULL,
    minor_category_id BIGINT,

    -- 지출 상세
    content VARCHAR(30),
    payment_amount DECIMAL(15, 2) NOT NULL,
    discount_amount DECIMAL(15, 2),
    actual_amount DECIMAL(15, 2),
    remark VARCHAR(50),

    -- 감사 필드 (BaseEntity)
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),

    -- 제약조건
    CONSTRAINT fk_expense_major_category
        FOREIGN KEY (major_category_id)
        REFERENCES category(id),

    CONSTRAINT fk_expense_minor_category
        FOREIGN KEY (minor_category_id)
        REFERENCES category(id),

    CONSTRAINT chk_expense_type
        CHECK (expense_type IN (0, 1)),

    CONSTRAINT chk_payment_amount
        CHECK (payment_amount >= 0),

    CONSTRAINT chk_discount_amount
        CHECK (discount_amount >= 0),

    CONSTRAINT chk_actual_amount
        CHECK (actual_amount >= 0)
);

-- 인덱스 생성
CREATE INDEX idx_expense_date
    ON expense(expense_date);

CREATE INDEX idx_expense_type
    ON expense(expense_type);

CREATE INDEX idx_expense_date_type
    ON expense(expense_date, expense_type);

CREATE INDEX idx_expense_major_category
    ON expense(major_category_id);

CREATE INDEX idx_expense_minor_category
    ON expense(minor_category_id);

-- 코멘트 추가
COMMENT ON TABLE expense IS '지출 정보';
COMMENT ON COLUMN expense.id IS '지출 ID';
COMMENT ON COLUMN expense.expense_date IS '지출일자';
COMMENT ON COLUMN expense.expense_type IS '지출 구분 (0: 고정지출, 1: 변동지출)';
COMMENT ON COLUMN expense.major_category_id IS '대분류 카테고리 ID';
COMMENT ON COLUMN expense.minor_category_id IS '소분류 카테고리 ID';
COMMENT ON COLUMN expense.content IS '지출내용';
COMMENT ON COLUMN expense.payment_amount IS '결재금액';
COMMENT ON COLUMN expense.discount_amount IS '할인금액';
COMMENT ON COLUMN expense.actual_amount IS '실지출금액';
COMMENT ON COLUMN expense.remark IS '비고';