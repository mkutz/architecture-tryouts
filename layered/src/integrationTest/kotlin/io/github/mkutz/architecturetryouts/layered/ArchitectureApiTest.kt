package io.github.mkutz.architecturetryouts.layered

import io.github.mkutz.architecturetryouts.layered.architecture.ArchitectureBuilder
import io.github.mkutz.architecturetryouts.layered.architecture.ArchitectureRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = [TestLayeredApplication::class]
)
class ArchitectureApiTest {

  @Value("http://localhost:\${local.server.port}") lateinit var baseUrl: String

  @Autowired lateinit var webClient: WebTestClient
  @Autowired lateinit var architectureRepository: ArchitectureRepository

  @Test
  @DisplayName("GET /architectures/")
  fun getAll() {
    val entity = architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response = webClient.get().uri("$baseUrl/architectures/").exchange()

    response.expectStatus().isOk().expectBody().jsonPath("$[0].id").isEqualTo("${entity.id}")
  }

  @Test
  @DisplayName("GET /architectures/:id")
  fun getOne() {
    val entity = architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response = webClient.get().uri("$baseUrl/architectures/${entity.id}").exchange()

    response.expectAll(
      { it.expectStatus().isOk() },
      { it.expectBody().jsonPath("$.id").isEqualTo("${entity.id}") }
    )
  }

  @Test
  @DisplayName("POST /architectures/")
  fun post() {
    val response =
      webClient
        .post()
        .uri("$baseUrl/architectures/")
        .header("Content-Type", "application/json")
        .bodyValue("""{"name":"Layered","notes":"Simple layered"}""")
        .exchange()

    response.expectAll(
      { it.expectStatus().isCreated() },
      { it.expectHeader().valueMatches("Location", "$baseUrl/architectures/.+") }
    )
  }

  @Test
  @DisplayName("POST /architectures/ invalid")
  fun postInvalid() {
    val response =
      webClient
        .post()
        .uri("$baseUrl/architectures/")
        .header("Content-Type", "application/json")
        .bodyValue("""{"name":"","pros":"Simple\nlayered"}""")
        .exchange()

    response.expectAll(
      { it.expectStatus().isBadRequest() },
      { it.expectHeader().doesNotExist("Location") },
      { it.expectBody().jsonPath("title", "Bad Request") },
      { it.expectBody().jsonPath("detail", "Invalid request content.") },
      { it.expectBody().jsonPath("status", "400") }
    )
  }

  @Test
  @DisplayName("PUT /architectures/:id")
  fun put() {
    val entity = architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response =
      webClient
        .put()
        .uri("$baseUrl/architectures/${entity.id}")
        .header("Content-Type", "application/json")
        .bodyValue(
          """{"name":"Layered","notes":"Simple layered","pros":["Few concepts","Lightweight"]}"""
        )
        .exchange()

    response.expectAll({ it.expectStatus().isOk() }) // TODO check more
  }

  @Test
  @DisplayName("DELETE /architectures/:id")
  fun delete() {
    val entity = architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response = webClient.delete().uri("$baseUrl/architectures/${entity.id}").exchange()

    response.expectAll({ it.expectStatus().isOk() })
  }
}
