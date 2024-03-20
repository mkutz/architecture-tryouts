package io.github.mkutz.architecturetryouts.layered.rating

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RatingService(private val repository: RatingRepository) {

  fun getRatingsForArchitecture(architectureId: UUID) =
      repository.findByArchitectureId(architectureId).map { Rating(it) }

  fun register(rating: Rating) = repository.save(RatingEntity(rating))
}
