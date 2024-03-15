package io.github.mkutz.architecturetryouts.hexagon

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName.parse

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class TestHexagonalApplication {

  @Bean
  @ServiceConnection
  fun postgreSQLContainer(): PostgreSQLContainer<*> {
    return PostgreSQLContainer(parse("postgres:9.6.12"))
  }
}

fun main(args: Array<String>) {
  fromApplication<HexagonalApplication>().with(TestHexagonalApplication::class).run(*args)
}
