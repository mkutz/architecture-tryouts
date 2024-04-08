plugins {
  id("com.diffplug.spotless") version "6.25.0"
  id("org.sonarqube") version "5.0.0.4638"
}

repositories { mavenCentral() }

sonar {
  properties {
    property("sonar.projectKey", "mkutz_architecture-tryouts")
    property("sonar.organization", "mkutz")
    property("sonar.host.url", "https://sonarcloud.io")
  }
}

spotless {
  format("misc") {
    target("**/*.md", "**/*.xml", "**/*.yml", "**/*.yaml", "**/*.html", "**/*.css", ".gitignore")
    targetExclude("**/build/**/*", "**/.idea/**")
    trimTrailingWhitespace()
    endWithNewline()
    indentWithSpaces(2)
  }

  freshmark { target("*.md") }

  java {
    target("**/*.java")
    targetExclude("**/build/**/*")
    googleJavaFormat().reflowLongStrings()
    removeUnusedImports()
    indentWithSpaces(2)
  }

  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  sql {
    target("**/*.sql")
    trimTrailingWhitespace()
    endWithNewline()
    dbeaver()
  }
}
