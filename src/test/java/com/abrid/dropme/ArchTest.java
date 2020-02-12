package com.abrid.dropme;

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
            .importPackages("com.abrid.dropme");

        noClasses()
            .that()
                .resideInAnyPackage("com.abrid.dropme.service..")
            .or()
                .resideInAnyPackage("com.abrid.dropme.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.abrid.dropme.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
