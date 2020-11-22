package com.examp.elastic;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.examp.elastic");

        noClasses()
            .that()
                .resideInAnyPackage("com.examp.elastic.service..")
            .or()
                .resideInAnyPackage("com.examp.elastic.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.examp.elastic.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
