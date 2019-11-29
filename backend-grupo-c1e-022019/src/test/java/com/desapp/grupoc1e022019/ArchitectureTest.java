package com.desapp.grupoc1e022019;


import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@RunWith(ArchUnitRunner.class) // Remove this line for JUnit 5!!
@AnalyzeClasses(packages = "com.desapp.grupoc1e022019")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule DAOs_must_reside_in_a_dao_package =
            classes().that().haveNameMatching(".*DAO").should().resideInAPackage("..persistence..")
                    .as("DAOs should reside in a package '..dao..'");

    @ArchTest
    public static final ArchRule only_DAOs_may_use_the_EntityManager =
            /*noClasses().that().resideOutsideOfPackage("..persistence..")
                    .should().accessClassesThat().resideInAPackage("..repositories..")
                    .andShould().haveNameMatching("GrupoC1e022019Application")
                    .as("Only DAOs may use the repositories" );*/
            classes().that().resideInAPackage("..repositories..").should()
                    .onlyBeAccessed().byClassesThat().resideInAPackage("..persistence..");


}