package io.github.mkutz.architecturetryouts.layered.architecture

import jakarta.transaction.Transactional
import java.util.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArchitectureService(private val repository: ArchitectureRepository, private val producer: ArchitectureProducer) {

  fun getAll() = repository.findAll().map { Architecture(it) }

  fun getById(id: String) = repository.findByIdOrNull(UUID.fromString(id))?.let { Architecture(it) }

  @Transactional
  fun create(architecture: Architecture): Architecture {
    val created = Architecture(repository.save(ArchitectureEntity(architecture)))
    producer.publish(ArchitectureMessage(created))
    return created
  }

  @Transactional
  fun update(architecture: Architecture): Architecture {
    val updated = Architecture(repository.save(ArchitectureEntity(architecture)))
    producer.publish(ArchitectureMessage(updated))
    return updated
  }

  fun delete(id: UUID) {
    repository.deleteById(id)
  }
}
