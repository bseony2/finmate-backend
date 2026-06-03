package com.finmate;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * Clean Architecture 및 DDD 레이어 규칙을 검증하는 아키텍처 테스트
 *
 * <p>검증 규칙:
 * <ul>
 *   <li>Domain 레이어는 어떤 레이어에도 의존하지 않음</li>
 *   <li>Application 레이어는 Domain만 의존</li>
 *   <li>Infrastructure와 Interfaces는 외부 레이어</li>
 * </ul>
 */
class ArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void setUp() {
        importedClasses = new ClassFileImporter()
                .importPackages("com.finmate");
    }

    /**
     * Domain 레이어가 Application 레이어를 의존하지 않는지 검증
     */
    @Test
    @DisplayName("Domain 레이어는 Application 레이어에 의존하지 않아야 한다")
    void domain_should_not_depend_on_application() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..application..");

        rule.check(importedClasses);
    }

    /**
     * Domain 레이어가 Infrastructure 레이어를 의존하지 않는지 검증
     */
    @Test
    @DisplayName("Domain 레이어는 Infrastructure 레이어에 의존하지 않아야 한다")
    void domain_should_not_depend_on_infrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }

    /**
     * Domain 레이어가 Interfaces 레이어를 의존하지 않는지 검증
     */
    @Test
    @DisplayName("Domain 레이어는 Interfaces 레이어에 의존하지 않아야 한다")
    void domain_should_not_depend_on_interfaces() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..interfaces..");

        rule.check(importedClasses);
    }

    /**
     * Application 레이어가 Infrastructure 레이어를 의존하지 않는지 검증
     */
    @Test
    @DisplayName("Application 레이어는 Infrastructure 레이어에 의존하지 않아야 한다")
    void application_should_not_depend_on_infrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }

    /**
     * Application 레이어가 Interfaces 레이어를 의존하지 않는지 검증
     */
    @Test
    @DisplayName("Application 레이어는 Interfaces 레이어에 의존하지 않아야 한다")
    void application_should_not_depend_on_interfaces() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..interfaces..");

        rule.check(importedClasses);
    }

    /**
     * 전체 레이어 아키텍처 규칙을 한 번에 검증
     */
    @Test
    @DisplayName("Clean Architecture 레이어 규칙을 모두 준수해야 한다")
    void should_follow_clean_architecture_layers() {
        ArchRule rule = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Domain").definedBy("..domain..")
                .layer("Application").definedBy("..application..")
                .layer("Infrastructure").definedBy("..infrastructure..")
                .layer("Interfaces").definedBy("..interfaces..")

                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure", "Interfaces")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure", "Interfaces")
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("Interfaces").mayNotBeAccessedByAnyLayer();

        rule.check(importedClasses);
    }
}