# Finmate Backend

개인 자산관리 앱 **Finmate**의 백엔드 서버입니다.
가계부 기능(수입/지출/저축)을 중심으로 Clean Architecture + DDD + Spring Modulith 패턴을 실습하기 위한 프로젝트입니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| Build | Gradle Kotlin DSL |
| ORM | Spring Data JPA |
| DB (운영) | PostgreSQL 14+ |
| DB (테스트) | H2 in-memory |
| 모듈화 | Spring Modulith 1.3.0 |
| 유효성 검증 | Spring Validation |
| 유틸 | Lombok |
| 아키텍처 테스트 | ArchUnit 1.2.1 |
| 테스트 | JUnit 5, AssertJ |

---

## 아키텍처

**Clean Architecture + DDD** 레이어 구조를 따릅니다.

```
interfaces (REST Controller, Request/Response DTO)
      ↓
application (Service, Command/Result DTO, Repository 인터페이스)
      ↓
domain (Entity, Value Object, AttributeConverter)
      ↑
infrastructure (JpaRepository, RepositoryImpl)
```

- **Domain** 레이어는 외부 레이어에 절대 의존하지 않습니다.
- **Application** 레이어는 Domain만 의존합니다 (Infrastructure/Interfaces 의존 금지).
- Repository 인터페이스는 Application 레이어에, 구현체는 Infrastructure 레이어에 위치합니다.
- `ArchitectureTest`가 위 규칙을 컴파일 후 자동 검증합니다.

---

## 패키지 구조

```
src/main/java/com/myfinance/
├── MyFinanceManagerApplication.java
├── ledger/
│   ├── application/
│   │   ├── category/
│   │   │   ├── CategoryRepository.java        # 인터페이스
│   │   │   ├── CategoryService.java
│   │   │   ├── CategoryTypeRepository.java
│   │   │   ├── CategoryTypeService.java
│   │   │   └── dto/  (CategoryCommand, CategoryResult)
│   │   ├── expense/
│   │   │   ├── ExpenseRepository.java
│   │   │   ├── ExpenseService.java
│   │   │   └── dto/  (ExpenseCommand, ExpenseResult)
│   │   ├── income/
│   │   │   ├── IncomeRepository.java
│   │   │   ├── IncomeService.java
│   │   │   └── dto/  (IncomeCommand, IncomeResult)
│   │   └── savings/
│   │       ├── SavingsRepository.java
│   │       ├── SavingsService.java
│   │       └── dto/  (SavingsCommand, SavingsResult)
│   ├── domain/
│   │   ├── account/   (Account, AccountStatus)
│   │   ├── category/  (Category, CategoryType, ExpenseType, ExpenseTypeConverter)
│   │   ├── common/    (BaseEntity)
│   │   ├── expense/   (Expense)
│   │   ├── income/    (Income)
│   │   └── savings/   (Savings)
│   ├── infrastructure/
│   │   └── persistence/
│   │       ├── category/  (JpaCategoryRepository, CategoryRepositoryImpl)
│   │       ├── expense/   (JpaExpenseRepository, ExpenseRepositoryImpl)
│   │       ├── income/    (JpaIncomeRepository, IncomeRepositoryImpl)
│   │       └── savings/   (JpaSavingsRepository, SavingsRepositoryImpl)
│   └── interfaces/
│       └── rest/
│           ├── category/  (CategoryController, CategoryTypeController)
│           ├── expense/   (ExpenseController)
│           ├── income/    (IncomeController)
│           └── savings/   (SavingsController)
└── shared/
    └── config/  (WebConfig)
```

### DTO 흐름

```
HTTP Request
  → Request.toCommand()
  → Service (Command)
  → Result.from(entity)
  → Response.from(result)
  → HTTP Response
```

---

## 로컬 실행

### 사전 요구사항

- Java 21+
- PostgreSQL 실행 중

### PostgreSQL 설정

```sql
CREATE DATABASE my_finance_manager;
-- 기본 접속 정보: localhost:5432 / postgres / postgres
```

스키마는 `src/main/resources/` 하위 SQL 파일을 순서대로 실행합니다.

```
schema_category.sql   -- category_type, category 테이블
schema_expense.sql    -- expense 테이블
```

### 애플리케이션 실행

```bash
./gradlew bootRun
```

서버가 기동되면 `http://localhost:8080`에서 API를 사용할 수 있습니다.

---

## API 엔드포인트

### 카테고리

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/categories?types=수입,지출` | 카테고리 트리 조회 |
| POST | `/api/categories` | 카테고리 생성 |
| PUT | `/api/categories/{id}` | 카테고리 수정 |
| DELETE | `/api/categories/{id}` | 카테고리 삭제 |

### 수입 (Income)

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/incomes?year={year}&month={month}` | 월별 수입 목록 |
| GET | `/api/incomes/{id}` | 수입 단건 조회 |
| POST | `/api/incomes` | 수입 생성 |
| PUT | `/api/incomes/{id}` | 수입 수정 |
| DELETE | `/api/incomes/{id}` | 수입 삭제 |

### 지출 (Expense)

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/expenses?year={year}&month={month}` | 월별 지출 목록 |
| GET | `/api/expenses?year={year}&month={month}&expenseType={0\|1}` | 고정/변동 지출 필터 |
| GET | `/api/expenses/{id}` | 지출 단건 조회 |
| POST | `/api/expenses` | 지출 생성 |
| PUT | `/api/expenses/{id}` | 지출 수정 |
| DELETE | `/api/expenses/{id}` | 지출 삭제 |

> `expenseType`: `0` = 고정지출, `1` = 변동지출

### 저축 (Savings)

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/api/savings/monthly/{year}/{month}` | 월별 저축 목록 |
| GET | `/api/savings/{id}` | 저축 단건 조회 |
| POST | `/api/savings` | 저축 생성 |
| PUT | `/api/savings/{id}` | 저축 수정 |
| DELETE | `/api/savings/{id}` | 저축 삭제 |

---

## 테스트

```bash
# 전체 테스트 실행 (H2 in-memory)
./gradlew test

# 아키텍처 규칙 검증만
./gradlew test --tests "com.myfinance.ArchitectureTest"
```

### 테스트 레이어

```
domain/       - 도메인 로직 단위 테스트 (순수 Java)
infrastructure/ - Repository 통합 테스트 (@DataJpaTest + H2)
application/  - Service 단위 테스트 (Mock Repository)
```

- `AbstractRepositoryTest`: 카테고리 공통 셋업 제공 (Repository 테스트 상속)
- `AbstractServiceTest`: Mock 기반 공통 셋업 제공 (Service 테스트 상속)

---

## 개발 규칙 요약

- **Builder 패턴 금지** → 정적 팩토리 메서드(`of()`, `create*()`) 사용
- **Repository 레벨 검증 금지** → Service 레벨에서 검증
- **AttributeConverter** → Infrastructure가 아닌 Domain 레이어에 배치
- **public 메서드** → JavaDoc 필수
- **테스트 메서드명** → BDD 스타일 snake_case
- **다른 모듈 간 직접 참조 금지** (같은 모듈 내 aggregate 간 직접 참조는 허용)
