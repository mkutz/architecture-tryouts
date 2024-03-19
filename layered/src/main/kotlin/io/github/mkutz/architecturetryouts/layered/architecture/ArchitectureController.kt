package io.github.mkutz.architecturetryouts.layered.architecture

import jakarta.validation.Valid
import java.time.Instant
import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/architectures")
class ArchitectureController(private val service: ArchitectureService) {

  @GetMapping(path = ["/"])
  fun getAll(): ResponseEntity<List<ArchitectureDto>> {
    return ok(service.getAll().map { ArchitectureDto(it) })
  }

  @GetMapping(path = ["/{id}"], produces = ["application/json"])
  fun getOne(@PathVariable id: String): ResponseEntity<ArchitectureDto> {
    val architecture = service.getById(id)
    return architecture?.let { ok(ArchitectureDto(it)) } ?: ResponseEntity.notFound().build()
  }

  @PostMapping(path = ["/"], consumes = ["application/json"], produces = ["application/json"])
  fun post(
    @Valid @RequestBody dto: ArchitectureRequestDto,
    uriComponentsBuilder: UriComponentsBuilder
  ): ResponseEntity<ArchitectureDto> {
    val createdArchitecture =
      service.create(
        Architecture(
          id = UUID.randomUUID(),
          name = dto.name,
          notes = dto.notes,
          pros = dto.pros,
          cons = dto.cons
        )
      )
    return created(
        uriComponentsBuilder
          .path("/architectures/{id}")
          .buildAndExpand(createdArchitecture.id)
          .toUri()
      )
      .body(ArchitectureDto(createdArchitecture))
  }

  @PutMapping(path = ["/{id}"], consumes = ["application/json"], produces = ["application/json"])
  fun put(
    @PathVariable id: String,
    @Valid @RequestBody dto: ArchitectureRequestDto
  ): ResponseEntity<ArchitectureDto> {
    val updatedArchitecture =
      service.update(
        Architecture(
          id = UUID.fromString(id),
          name = dto.name,
          notes = dto.notes,
          pros = dto.pros,
          cons = dto.cons,
          updated = Instant.now()
        )
      )
    return ok(ArchitectureDto(updatedArchitecture))
  }

  @DeleteMapping(path = ["/{id}"])
  fun delete(@PathVariable id: String): ResponseEntity<Unit> {
    service.delete(UUID.fromString(id))
    return ok().build()
  }
}
