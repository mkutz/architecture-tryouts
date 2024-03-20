package io.github.mkutz.architecturetryouts.layered.rating

import java.util.*

data class Rating(
  val id: UUID,
  val architectureId: UUID,
  val userId: UUID,
  val stars: Stars,
  val comment: String
) {

  constructor(
    entity: RatingEntity
  ) : this(
    id = entity.id,
    architectureId = entity.architectureId,
    userId = entity.userId,
    stars = Stars.entries[entity.stars],
    comment = entity.comment
  )

  constructor(message: RatingMessage) : this(
    id = message.id,
    architectureId = message.architectureId,
    userId = message.userId,
    stars = Stars.entries[message.stars],
    comment = message.comment
  )

  enum class Stars(val value: Int) {
    ONE(value = 1),
    TWO(value = 2),
    THREE(value = 3),
    FOUR(value = 4),
    FIVE(value = 5)
  }
}
