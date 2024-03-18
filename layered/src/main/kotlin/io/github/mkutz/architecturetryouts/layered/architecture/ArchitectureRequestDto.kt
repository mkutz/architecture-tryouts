package io.github.mkutz.architecturetryouts.layered.architecture

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ArchitectureRequestDto(
  @field:NotBlank @field:Pattern(regexp = "[\\p{L}\\p{N}\\p{Zs}\\p{P}]+") val name: String,
  val notes: String = "",
  val pros: List<@NotBlank @Pattern(regexp = "[\\p{L}\\p{N}\\p{Zs}\\p{P}]+") String> = listOf(),
  val cons: List<@NotBlank @Pattern(regexp = "[\\p{L}\\p{N}\\p{Zs}\\p{P}]+") String> = listOf()
)
