package br.com.itau.pld.demoapi.arquitetura;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;

import static br.com.itau.pld.demoapi.arquitetura.LayersEnum.REPOSITORY;
import static br.com.itau.pld.demoapi.arquitetura.PackageEnum.pathsPerLayer;
import static br.com.itau.pld.demoapi.arquitetura.PackagesUtil.MAIN_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@Category(ArchiectureTestMarker.class)
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = MAIN_PACKAGE)
public class DaoRulesTest {

    @ArchTest
    public static final ArchRule testaRepositoriesNoPacoteCorreto =
		    classes()
			.that()
			.areAssignableTo(JpaRepository.class)
			.should()
			.resideInAnyPackage(pathsPerLayer(REPOSITORY))
			.as("Entidades devem estar no pacote 'domain'");
}
