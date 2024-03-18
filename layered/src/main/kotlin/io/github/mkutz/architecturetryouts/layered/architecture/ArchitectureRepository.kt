package io.github.mkutz.architecturetryouts.layered.architecture

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface ArchitectureRepository : CrudRepository<ArchitectureEntity, UUID>
