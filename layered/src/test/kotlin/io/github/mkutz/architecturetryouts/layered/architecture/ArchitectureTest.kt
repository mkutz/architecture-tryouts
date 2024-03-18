package io.github.mkutz.architecturetryouts.layered.architecture

import java.time.Instant
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test

class ArchitectureTest {

  @Test
  fun `constructor entity`() {
    val entity =
      ArchitectureEntity(
        id = UUID.randomUUID(),
        name = "Test",
        notes = "Notes",
        pros = "Pro 1\nPro 2",
        cons = "Con 1\nCon 2",
        created = Instant.now(),
        updated = Instant.now()
      )

    val architecture = Architecture(entity)

    assertAll(
      { assertThat(architecture.id).isEqualTo(entity.id) },
      { assertThat(architecture.name).isEqualTo(entity.name) },
      { assertThat(architecture.notes).isEqualTo(entity.notes) },
      { assertThat(architecture.pros).isEqualTo(entity.pros.split("\n")) },
      { assertThat(architecture.cons).isEqualTo(entity.cons.split("\n")) },
      { assertThat(architecture.created).isEqualTo(entity.created) },
      { assertThat(architecture.updated).isEqualTo(entity.updated) }
    )
  }
}
