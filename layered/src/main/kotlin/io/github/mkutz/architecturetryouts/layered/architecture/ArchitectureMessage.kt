package io.github.mkutz.architecturetryouts.layered.architecture

import java.time.format.DateTimeFormatter

data class ArchitectureMessage(
  val id: String,
  val name: String,
  val notes: String,
  val pros: List<String>,
  val cons: List<String>,
  val created: String,
  val updated: String?
) {
  constructor(
    bo: Architecture
  ) : this(
    id = bo.id.toString(),
    name = bo.name,
    notes = bo.notes,
    pros = bo.pros,
    cons = bo.cons,
    created = DateTimeFormatter.ISO_INSTANT.format(bo.created),
    updated = DateTimeFormatter.ISO_INSTANT.format(bo.updated)
  )
}
