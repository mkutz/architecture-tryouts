package io.github.mkutz.architecturetryouts.layered.architecture

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

data class ArchitectureBuilder(
  var id: UUID = UUID.randomUUID(),
  var name: String = "Test Architecture",
  var notes: String = "Test Description",
  var pros: List<String> = listOf("Test Pro 1", "Test Pro 2"),
  var cons: List<String> = listOf("Test Con 1", "Test Con 2"),
  var created: Instant = Instant.now().minus(1, ChronoUnit.DAYS),
  var updated: Instant? = Instant.now()
) {

  fun id(id: UUID) = apply { this.id = id }

  fun name(name: String) = apply { this.name = name }

  fun notes(notes: String) = apply { this.notes = notes }

  fun pros(pros: List<String>) = apply { this.pros = pros }

  fun cons(cons: List<String>) = apply { this.cons = cons }

  fun created(created: Instant) = apply { this.created = created }

  fun updated(updated: Instant) = apply { this.updated = updated }

  fun build(): Architecture =
    Architecture(
      id = id,
      name = name,
      notes = notes,
      pros = pros,
      cons = cons,
      created = created,
      updated = updated
    )

  fun buildEntity(): ArchitectureEntity = ArchitectureEntity(this.build())
}
