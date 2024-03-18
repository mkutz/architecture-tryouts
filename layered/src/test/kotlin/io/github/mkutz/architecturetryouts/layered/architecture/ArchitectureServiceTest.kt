package io.github.mkutz.architecturetryouts.layered.architecture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArchitectureServiceTest {

  private val repository = ArchitectureRepositoryStub()

  private val service = ArchitectureService(repository)

  @Test
  fun `getAll empty`() {
    assertThat(service.getAll()).isEmpty()
  }
}
