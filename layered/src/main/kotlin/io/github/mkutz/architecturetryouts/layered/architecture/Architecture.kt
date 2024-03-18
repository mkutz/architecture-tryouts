package io.github.mkutz.architecturetryouts.layered.architecture

import java.time.Instant
import java.util.UUID

data class Architecture(
  val id: UUID,
  val name: String,
  val notes: String = "",
  val pros: List<String> = emptyList(),
  val cons: List<String> = emptyList(),
  val created: Instant = Instant.now(),
  val updated: Instant? = created
) {
  constructor(
    entity: ArchitectureEntity
  ) : this(
    id = entity.id,
    name = entity.name,
    notes = entity.notes,
    pros = entity.pros.split("\n"),
    cons = entity.cons.split("\n"),
    created = entity.created,
    updated = entity.updated
  )
}
