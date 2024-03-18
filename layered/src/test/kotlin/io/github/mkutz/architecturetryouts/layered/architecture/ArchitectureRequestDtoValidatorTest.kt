package io.github.mkutz.architecturetryouts.layered.architecture

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class ArchitectureRequestDtoValidatorTest {

  private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

  @TestFactory
  fun valid() =
    listOf(
        "minimal" to ArchitectureRequestDto(name = "Minimal"),
        "with notes" to
          ArchitectureRequestDto(name = "With notes", notes = "Some notes. Which are interesting!"),
        "with pros and cons" to
          ArchitectureRequestDto(
            name = "With pros and cons",
            notes = "Some notes. Which are interesting!",
            pros = listOf("Lightweight", "Few concepts"),
            cons = listOf("Little isolation")
          )
      )
      .map { (description, dto) ->
        dynamicTest("validate(${description})") { assertThat(validator.validate(dto)).isEmpty() }
      }

  @TestFactory
  fun invalid() =
    listOf(
        "blank name" to ArchitectureRequestDto(name = ""),
        "invalid spaces" to
          ArchitectureRequestDto(
            name = "Layered",
            notes = "Some notes. Which are interesting!",
            pros = listOf("Light\nweight"),
            cons = listOf("Little\tisolation")
          )
      )
      .map { (description, dto) ->
        dynamicTest("validate(${description})") { assertThat(validator.validate(dto)).isNotEmpty() }
      }
}
