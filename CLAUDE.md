# 백엔드 지침 (my-finance-manager-backend)

> 공통 규칙은 상위 디렉토리의 CLAUDE.md 참고

---

## 기술 스택

- Java 21
- Spring Boot 3.5.6
- Gradle Kotlin DSL (`build.gradle.kts`)
- Spring Data JPA + H2 (테스트) / PostgreSQL (운영)
- Spring Modulith 1.3.0
- Spring Validation
- Lombok
- ArchUnit 1.2.1 (아키텍처 테스트)
- JUnit 5 + AssertJ

---

## 아키텍처

- **패턴:** Clean Architecture + DDD + Spring Modulith
- **바운디드 컨텍스트:** Ledger 모듈 하위에 Category, Income, Expense, Savings Aggregate 구성
- **MSA 전환 계획:** 모듈별 독립 build.gradle 구성으로 분리 배포 용이하게 설계

```
interfaces (Controller, Request/Response DTO)
    ↓
application (Service, Command/Result DTO, Repository Interface)
    ↓
domain (Entity, Value Object, Converter)
    ↑
infrastructure (RepositoryImpl, JpaRepository)
```

### 패키지 구조
```
com.myfinance
├── ledger/
│   ├── application/
│   │   ├── category/    (CategoryService, CategoryRepository 인터페이스)
│   │   ├── expense/     (ExpenseService, ExpenseRepository 인터페이스)
│   │   ├── income/      (IncomeService, IncomeRepository 인터페이스)
│   │   └── savings/     (SavingsService, SavingsRepository 인터페이스)
│   ├── domain/
│   │   ├── account/     (Account, AccountStatus)
│   │   ├── category/    (Category, CategoryType, ExpenseType, ExpenseTypeConverter)
│   │   ├── common/      (BaseEntity)
│   │   ├── expense/     (Expense)
│   │   ├── income/      (Income)
│   │   └── savings/     (Savings)
│   ├── infrastructure/
│   │   └── persistence/ (JpaRepository + RepositoryImpl 구현체)
│   └── interfaces/
│       └── rest/        (Controller, Request/Response DTO)
└── shared/
    └── config/          (WebConfig 등 공통 설정)
```

### ArchUnit 아키텍처 규칙 (`ArchitectureTest`)
- Domain은 어떤 상위 레이어도 의존 금지
- Application은 Infrastructure / Interfaces 의존 금지
- Infrastructure / Interfaces는 다른 레이어에서 접근 불가

---

## 개발 규칙

### 기본
- DDD 아키텍처를 고려하여 개발
- **TDD 방식**으로 개발 (Red-Green-Refactor 사이클 엄수)
- 테스트 레이어: Repository → Service → Controller 순서로 작성
- public 메서드는 **JavaDoc 필수** 작성
  ```java
  /**
   * 수입 항목을 등록한다.
   *
   * @param command 수입 등록 커맨드 객체
   * @return 등록된 수입 ID
   */
  ```

### DTO 흐름
```
Request → toCommand() → Command → Service → Result → Response.from(result)
```
- `Request` / `Response` : interfaces 레이어 (HTTP 경계)
- `Command` / `Result`   : application 레이어 (도메인 경계)
- 레이어 간 변환은 항상 **정적 팩토리 메서드** 사용 (`from()`, `toCommand()`)

### 테스트
- BDD 스타일 (snake_case 메서드명)
- `AbstractRepositoryTest` / `AbstractServiceTest` 상속으로 공통 셋업 재사용
- `@BeforeEach` + 공유 테스트 데이터로 중복 최소화
- 커스텀 비즈니스 로직 검증에 집중
- 프레임워크 동작 확인용 의미없는 테스트 작성 금지
- 테스트 DB: H2 in-memory (`ddl-auto: create-drop`), 운영 DB: PostgreSQL

---

## 금지 패턴 ❌
- Builder 패턴 사용 금지 → **정적 팩토리 메서드** 사용
- Repository 레벨 검증 금지 → **Service 레벨**에서 검증
- **다른 모듈 간 직접 참조 금지** (같은 모듈 내 aggregate 간 직접 참조는 허용)
- AttributeConverter는 infrastructure가 아닌 **domain 레이어**에 배치
- 프레임워크 기능만 확인하는 의미없는 테스트 작성 금지

---

## 선호 패턴 ✅
- 정적 팩토리 메서드로 도메인 무결성 강화
- Service 레벨 검증으로 캡슐화
- 컴파일 타임 안전성 우선

---

## 로컬 실행

### 사전 요구사항
- Java 21+
- PostgreSQL 실행 중 (`localhost:5432`, DB: `my_finance_manager`)

### 실행
```bash
./gradlew bootRun
# 서버: http://localhost:8080
```

### 테스트
```bash
./gradlew test
./gradlew test --tests "com.myfinance.ArchitectureTest"
```

---

## 현재 TODO
- [ ] 모듈별 독립 build.gradle 구성
- [ ] 전역 예외 처리 (`@RestControllerAdvice`) 추가
- [ ] API 인증/인가 (Spring Security) 추가

---

## 주요 학습 내용 (축적)
- 같은 바운디드 컨텍스트 내 aggregate는 서로 참조 가능 (다른 모듈은 불가)
- Service 레벨 검증이 Repository 레벨보다 캡슐화에 유리
- AttributeConverter는 domain 레이어에 속함
- 정적 팩토리 메서드가 Builder보다 도메인 무결성에 유리
- 의미있는 테스트 = 커스텀 비즈니스 로직 검증, 프레임워크 동작 확인 X
