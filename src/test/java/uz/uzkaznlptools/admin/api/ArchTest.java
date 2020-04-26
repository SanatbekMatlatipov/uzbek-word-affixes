package uz.uzkaznlptools.admin.api;

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
            .importPackages("uz.uzkaznlptools.admin.api");

        noClasses()
            .that()
                .resideInAnyPackage("uz.uzkaznlptools.admin.api.service..")
            .or()
                .resideInAnyPackage("uz.uzkaznlptools.admin.api.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..uz.uzkaznlptools.admin.api.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
