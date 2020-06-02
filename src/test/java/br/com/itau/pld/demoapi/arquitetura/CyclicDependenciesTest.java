package br.com.itau.pld.demoapi.arquitetura;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static br.com.itau.pld.demoapi.arquitetura.PackagesUtil.MAIN_PACKAGE;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@Category(ArchiectureTestMarker.class)
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = MAIN_PACKAGE)
public class CyclicDependenciesTest {

  @ArchTest
  public static final ArchRule testaDependenciaCiclica =
      slices().matching(MAIN_PACKAGE).should().beFreeOfCycles();
}
