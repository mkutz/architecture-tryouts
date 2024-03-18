package io.github.mkutz.architecturetryouts.layered.architecture

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "architectures")
data class ArchitectureEntity(
  @Id val id: UUID,
  val name: String,
  val notes: String,
  val pros: String,
  val cons: String,
  val created: Instant,
  val updated: Instant?
) {
  constructor(
    bo: Architecture
  ) : this(
    bo.id,
    bo.name,
    bo.notes,
    bo.pros.joinToString("\n"),
    bo.cons.joinToString("\n"),
    bo.created,
    bo.updated
  )
}
