import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
  val kotlinVersion = "1.9.23"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion
  `jvm-test-suite`
  `java-test-fixtures`
}

java { sourceCompatibility = JavaVersion.VERSION_21 }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  runtimeOnly("org.postgresql:postgresql")
}

testing {
  suites {
    withType(JvmTestSuite::class).configureEach {
      useJUnitJupiter()
      dependencies {
        implementation("org.junit.jupiter:junit-jupiter-api")
        implementation("org.assertj:assertj-core")
        val approvaltestsVersion = "23.0.1"
        implementation("com.approvaltests:approvaltests:$approvaltestsVersion")
      }
    }

    val test by
      getting(JvmTestSuite::class) {
        dependencies { implementation("org.junit.jupiter:junit-jupiter-params") }
      }

    register<JvmTestSuite>("architectureTest") {
      dependencies {
        implementation(project())
        val archunitVersion = "1.2.1"
        implementation("com.tngtech.archunit:archunit-junit5:$archunitVersion")
      }
      targets { all { testTask.configure { shouldRunAfter(test) } } }
    }

    register<JvmTestSuite>("integrationTest") {
      dependencies {
        implementation(project())
        implementation(testFixtures(project()))
        implementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-testcontainers")
        implementation("org.testcontainers:junit-jupiter")
        implementation("org.testcontainers:postgresql")
        implementation("org.testcontainers:kafka")
      }
      testType = TestSuiteType.INTEGRATION_TEST
      targets { all { testTask.configure { shouldRunAfter(test) } } }
    }
  }
}

tasks.check {
  dependsOn(testing.suites.named("architectureTest"), testing.suites.named("integrationTest"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    freeCompilerArgs += "-Xemit-jvm-type-annotations"
    jvmTarget = "21"
  }
}
