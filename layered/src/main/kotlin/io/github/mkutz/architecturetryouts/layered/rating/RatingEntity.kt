package io.github.mkutz.architecturetryouts.layered.rating

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "ratings")
data class RatingEntity(
  @Id val id: UUID,
  val architectureId: UUID,
  val userId: UUID,
  val stars: Int,
  val comment: String
) {

  constructor(bo: Rating) : this(bo.id, bo.architectureId, bo.userId, bo.stars.value, bo.comment)
}
