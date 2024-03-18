package io.github.mkutz.architecturetryouts.layered.architecture

import io.github.mkutz.architecturetryouts.layered.CrudRepositoryStub
import java.util.*

class ArchitectureRepositoryStub :
  CrudRepositoryStub<ArchitectureEntity, UUID>(), ArchitectureRepository {

  override fun <S : ArchitectureEntity?> save(entity: S & Any): S & Any {
    return entity.also { data[it.id] = it }
  }

  override fun <S : ArchitectureEntity> saveAll(entities: Iterable<S>): Iterable<S> {
    data.putAll(entities.associateBy { it.id })
    return entities
  }
}
