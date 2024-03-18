package io.github.mkutz.architecturetryouts.layered

import com.tngtech.archunit.base.DescribedPredicate.or
import com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture

@AnalyzeClasses(packagesOf = [LayeredArchitectureTest::class])
class LayeredArchitectureTest {

  @ArchTest
  val layeredArchitecture =
    layeredArchitecture()
      .consideringOnlyDependenciesInLayers()
      .layer("Controllers")
      .definedBy(or(simpleNameEndingWith("Controller"), simpleNameEndingWith("Dto")))
      .layer("Services")
      .definedBy(or(simpleNameEndingWith("Service")))
      .layer("Persistence")
      .definedBy(or(simpleNameEndingWith("Respository"), simpleNameEndingWith("Entity")))
      .whereLayer("Controllers")
      .mayNotBeAccessedByAnyLayer()
      .whereLayer("Services")
      .mayOnlyBeAccessedByLayers("Controllers")
      .whereLayer("Persistence")
      .mayOnlyAccessLayers("Services")
}
