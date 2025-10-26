-- ============================================
-- 카테고리 타입 데이터 삽입
-- ============================================
INSERT INTO category_type (id, name) VALUES (1, '가계부');

-- ============================================
-- 최상위 카테고리 삽입
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1, '수입', NULL, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (2, '저축성지출', NULL, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (3, '식비', NULL, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (4, '주거비', NULL, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (5, '생활용품비', NULL, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (6, '반려동물비', NULL, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (7, '의류미용비', NULL, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (8, '교육비', NULL, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (9, '문화생활비', NULL, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (10, '의료비', NULL, 1, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (11, '유류교통비', NULL, 1, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (12, '통신비', NULL, 1, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (13, '경조사회비', NULL, 1, 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (14, '용돈', NULL, 1, 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (15, '추가항목', NULL, 1, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 수입 (parent_id = 1)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (100, '이월', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (101, '급여', 1, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (102, '수당', 1, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (103, '상여', 1, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (104, '투자수익', 1, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (105, '이자', 1, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (106, '부수익', 1, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (107, '처분소득', 1, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (108, '기타 수입', 1, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 저축성지출 (parent_id = 2)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (200, '예/적금', 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (201, '주택청약', 2, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (202, '퇴직연금', 2, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (203, '비상금', 2, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (204, '투자', 2, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (205, '기타 저축', 2, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 식비 (parent_id = 3)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (300, '시장/마트/편의점', 3, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (301, '외식/배달', 3, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (302, '간식', 3, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (303, '술/회식', 3, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (304, '커피숍', 3, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (305, '식재료비', 3, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (306, '기타 식비', 3, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 주거비 (parent_id = 4)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (400, '재산세', 4, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (401, '월세', 4, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (402, '주담대 이자', 4, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (403, '주담대 원금', 4, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (404, '관리비', 4, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (405, '수도세', 4, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (406, '전기세', 4, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (407, '가스비', 4, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (408, '기타 주거비', 4, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 생활용품비 (parent_id = 5)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (500, '가구/가전', 5, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (501, '주방/욕실', 5, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (502, '오피스/문구', 5, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (503, '멤버십', 5, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (504, '기타 생활용품', 5, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (505, '기타 잡지출', 5, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 반려동물비 (parent_id = 6)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (600, '사료/간식', 6, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (601, '용품/장난감', 6, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (602, '병원/접종', 6, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (603, '기타 반려동물', 6, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 의류미용비 (parent_id = 7)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (700, '의류', 7, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (701, '패션잡화', 7, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (702, '세탁비', 7, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (703, '미용', 7, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (704, '화장품', 7, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (705, '미용실', 7, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (706, '기타 미용', 7, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 교육비 (parent_id = 8)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (800, '학교', 8, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (801, '학원', 8, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (802, '도서', 8, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (803, '강의', 8, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (804, '기타 교육', 8, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 문화생활비 (parent_id = 9)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (900, '영화/관람', 9, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (901, '여가', 9, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (902, '여행', 9, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (903, '숙박', 9, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (904, 'OTT', 9, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (905, '술/유흥', 9, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (906, '운동', 9, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (907, '프리다이빙', 9, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (908, '기타 문화생활', 9, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 의료비 (parent_id = 10)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1000, '병원', 10, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1001, '의약품', 10, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1002, '영양제', 10, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1003, '보험료', 10, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1004, '기타 의료비', 10, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 유류교통비 (parent_id = 11)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1100, '자동차보험', 11, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1101, '자동차할부', 11, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1102, '자동차세', 11, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1103, '유류비/충전비', 11, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1104, '수리비', 11, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1105, '톨비/하이패스', 11, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1106, '주차비', 11, 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1107, '버스/지하철', 11, 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1108, '택시', 11, 1, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1109, '기차', 11, 1, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1110, '항공', 11, 1, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1111, '기타 교통', 11, 1, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 통신비 (parent_id = 12)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1200, '핸드폰', 12, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1201, '인터넷', 12, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1202, 'IPTV', 12, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1203, '우편/택배', 12, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1204, '기타 통신', 12, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1205, '멤버십', 12, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 경조사회비 (parent_id = 13)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1300, '축의금', 13, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1301, '조의금', 13, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1302, '기부금', 13, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1303, '모임회비', 13, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1304, '선물', 13, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1305, '기타 경조사', 13, 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 하위 카테고리 삽입 - 용돈 (parent_id = 14)
-- ============================================
INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1400, '부모님 용돈', 14, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1401, '할머니 용돈', 14, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1402, '첫째 용돈', 14, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO category (id, name, parent_id, category_type_id, display_order, created_at, updated_at)
VALUES (1403, '기타 용돈', 14, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
