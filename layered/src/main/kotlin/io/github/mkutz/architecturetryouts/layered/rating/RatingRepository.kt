package io.github.mkutz.architecturetryouts.layered.rating

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface RatingRepository : CrudRepository<RatingEntity, UUID> {
  
  fun findByArchitectureId(architectureId: UUID): List<RatingEntity>
}
