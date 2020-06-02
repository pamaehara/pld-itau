package br.com.itau.pld.demoapi.arquitetura;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static br.com.itau.pld.demoapi.arquitetura.LayersEnum.*;
import static br.com.itau.pld.demoapi.arquitetura.PackageEnum.pathsPerLayer;
import static br.com.itau.pld.demoapi.arquitetura.PackagesUtil.MAIN_PACKAGE;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@Category(ArchiectureTestMarker.class)
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = MAIN_PACKAGE)
public class LayerRulesTest {

    @ArchTest
    public static final ArchRule testaAcessoEntreCamadas = layeredArchitecture()
        .layer("Controllers").definedBy(pathsPerLayer(CONTROLLER))
        .layer("Services").definedBy(pathsPerLayer(SERVICE))
        .layer("Repository").definedBy(pathsPerLayer(REPOSITORY))

        .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
        .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
        .whereLayer("Repository").mayOnlyBeAccessedByLayers("Services");
}
