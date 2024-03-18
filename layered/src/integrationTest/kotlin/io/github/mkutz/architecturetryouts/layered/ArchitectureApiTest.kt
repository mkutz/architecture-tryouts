package io.github.mkutz.architecturetryouts.layered

import com.fasterxml.jackson.databind.json.JsonMapper
import io.github.mkutz.architecturetryouts.layered.architecture.ArchitectureBuilder
import io.github.mkutz.architecturetryouts.layered.architecture.ArchitectureRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = [TestLayeredApplication::class]
)
class ArchitectureApiTest {

  @Value("http://localhost:\${local.server.port}") lateinit var baseUrl: String

  @Autowired lateinit var restTemplate: TestRestTemplate
  @Autowired lateinit var architectureRepository: ArchitectureRepository

  val jsonMapper: JsonMapper = JsonMapper.builder().build()

  @Test
  @DisplayName("GET /architectures/")
  fun getAll() {
    architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response = restTemplate.getForEntity<String>("$baseUrl/architectures/")

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(jsonMapper.readValue(response.body, List::class.java)).hasSize(1)
  }

  @Test
  @DisplayName("GET /architectures/:id")
  fun getOne() {
    val architecture = architectureRepository.save(ArchitectureBuilder().buildEntity())

    val response = restTemplate.getForEntity<String>("$baseUrl/architectures/${architecture.id}")

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
  }

  @Test
  @DisplayName("POST /architectures/")
  fun post() {
    val response =
      restTemplate.postForEntity(
        "$baseUrl/architectures/",
        """{"name":"Layered","notes":"Simple layered"}""",
        String::class.java
      )

    assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
  }
}
