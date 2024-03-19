package io.github.mkutz.architecturetryouts.layered

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName.parse

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
class TestLayeredApplication {

  @Bean
  @ServiceConnection
  fun postgreSQLContainer() =
    PostgreSQLContainer(parse("postgres:16").asCompatibleSubstituteFor("postgres"))

  @Bean
  @ServiceConnection
  fun kafkaContainer() = KafkaContainer(parse("confluentinc/cp-kafka:7.6.0"))
}

fun main(args: Array<String>) {
  fromApplication<LayeredApplication>().with(TestLayeredApplication::class).run(*args)
}
