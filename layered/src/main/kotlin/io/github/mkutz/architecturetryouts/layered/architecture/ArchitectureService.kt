package io.github.mkutz.architecturetryouts.layered.architecture

import java.util.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArchitectureService(private val repository: ArchitectureRepository) {

  fun getAll() = repository.findAll().map { Architecture(it) }

  fun getById(id: String) = repository.findByIdOrNull(UUID.fromString(id))?.let { Architecture(it) }

  fun create(architecture: Architecture) =
    Architecture(repository.save(ArchitectureEntity(architecture)))

  fun update(architecture: Architecture) =
    Architecture(repository.save(ArchitectureEntity(architecture)))

  fun delete(id: UUID) {
    repository.deleteById(id)
  }
}
