package io.github.mkutz.architecturetryouts.layered.architecture

import java.util.UUID.randomUUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder

class ArchitectureControllerTest {

  private val repository = ArchitectureRepositoryStub()

  private val controller = ArchitectureController(ArchitectureService(repository))

  @Test
  fun getAll() {
    val entities =
      repository
        .saveAll(
          listOf(
            ArchitectureBuilder().buildEntity(),
            ArchitectureBuilder().buildEntity(),
            ArchitectureBuilder().buildEntity()
          )
        )
        .toList()

    val response = controller.getAll()

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).hasSize(entities.size)
    assertThat(response.body?.map { it.id }).containsAll(entities.map { it.id.toString() })
  }

  @Test
  fun `getAll empty`() {
    val response = controller.getAll()

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isEmpty()
  }

  @Test
  fun getOne() {
    val entity = repository.save(ArchitectureBuilder().buildEntity())

    val response = controller.getOne(entity.id.toString())

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body)
      .hasFieldOrPropertyWithValue("id", entity.id.toString())
      .hasFieldOrPropertyWithValue("name", entity.name)
      .hasFieldOrPropertyWithValue("notes", entity.notes)
      .hasFieldOrPropertyWithValue("pros", entity.pros.split("\n"))
      .hasFieldOrPropertyWithValue("cons", entity.cons.split("\n"))
  }

  @Test
  fun `getOne unknown`() {
    val unknownId = randomUUID().toString()

    val response = controller.getOne(unknownId)

    assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
  }

  @Test
  fun post() {
    val requestBody =
      ArchitectureRequestDto(
        name = "Some name",
        notes = "Some notes",
        pros = listOf("Some pro"),
        cons = listOf("Some con")
      )

    val response = controller.post(requestBody, UriComponentsBuilder.newInstance())

    assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
    assertThat(response.body?.id).isNotNull()
    assertThat(response.headers.location?.path).endsWith(response.body?.id)
    assertThat(response.body)
      .hasFieldOrPropertyWithValue("name", requestBody.name)
      .hasFieldOrPropertyWithValue("notes", requestBody.notes)
      .hasFieldOrPropertyWithValue("pros", requestBody.pros)
      .hasFieldOrPropertyWithValue("cons", requestBody.cons)
      .hasNoNullFieldsOrProperties()
  }

  @Test
  fun put() {
    val entity = repository.save(ArchitectureBuilder().buildEntity())
    val requestBody =
      ArchitectureRequestDto(
        name = "Updated name",
        notes = "Updated notes",
        pros = listOf("Some pro", "Another pro"),
        cons = listOf("Some con", "Another con")
      )

    val response = controller.put(entity.id.toString(), requestBody)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body)
      .hasFieldOrPropertyWithValue("name", requestBody.name)
      .hasFieldOrPropertyWithValue("notes", requestBody.notes)
      .hasFieldOrPropertyWithValue("pros", requestBody.pros)
      .hasFieldOrPropertyWithValue("cons", requestBody.cons)
      .hasNoNullFieldsOrProperties()
  }

  @Test
  fun delete() {
    val entity = repository.save(ArchitectureBuilder().buildEntity())

    val response = controller.delete(entity.id.toString())

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(repository.findById(entity.id)).isNotPresent()
  }
}
