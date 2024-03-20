package io.github.mkutz.architecturetryouts.layered.rating

import org.hibernate.validator.constraints.Range
import java.util.*
import org.hibernate.validator.constraints.UUID as ValidUUID

data class RatingMessage(
  @field:ValidUUID val id: UUID,
  @field:ValidUUID val architectureId: UUID,
  @field:ValidUUID val userId: UUID,
  @field:Range(min = 1, max = 5) val stars: Int,
  val comment: String
)
