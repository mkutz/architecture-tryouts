package io.github.mkutz.architecturetryouts.layered.rating

import io.github.mkutz.architecturetryouts.layered.CrudRepositoryStub
import java.util.*

class RatingRepositoryStub : RatingRepository, CrudRepositoryStub<RatingEntity, UUID>() {

  override fun findByArchitectureId(architectureId: UUID) =
    data.values.filter { it.architectureId == architectureId }

  override fun <S : RatingEntity?> save(entity: S & Any) = entity.also { data[it.id] = it }

  override fun <S : RatingEntity> saveAll(entities: Iterable<S>) =
    entities.also { entities.forEach { data[it.id] = it } }
}
