-- ============================================
-- 테이블 삭제 (기존 데이터 초기화)
-- ============================================
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS category_type;

-- ============================================
-- CategoryType 테이블 생성
-- ============================================
CREATE TABLE category_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

-- ============================================
-- Category 테이블 생성
-- ============================================
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    parent_id BIGINT,
    category_type_id BIGINT NOT NULL,
    display_order INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE CASCADE,
    FOREIGN KEY (category_type_id) REFERENCES category_type(id) ON DELETE CASCADE
);

-- ============================================
-- 인덱스 생성
-- ============================================
CREATE INDEX idx_category_parent ON category(parent_id);
CREATE INDEX idx_category_type ON category(category_type_id);
CREATE INDEX idx_category_display_order ON category(display_order);