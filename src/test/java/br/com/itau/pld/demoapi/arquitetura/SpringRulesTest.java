package br.com.itau.pld.demoapi.arquitetura;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;

import static br.com.itau.pld.demoapi.arquitetura.LayersEnum.CONFIGURATION;
import static br.com.itau.pld.demoapi.arquitetura.PackageEnum.pathsPerLayer;
import static br.com.itau.pld.demoapi.arquitetura.PackagesUtil.MAIN_PACKAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@Category(ArchiectureTestMarker.class)
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = MAIN_PACKAGE)
public class SpringRulesTest {

    @ArchTest
    public static final ArchRule testaConfigDoSpringPacoteCorreto =
		    classes()
			.that()
			.areAnnotatedWith(Configuration.class)
			.should()
			.resideInAnyPackage(pathsPerLayer(CONFIGURATION))
			.as("Configuração de Spring devem estar no pacote '..demoapi.config'");

}
