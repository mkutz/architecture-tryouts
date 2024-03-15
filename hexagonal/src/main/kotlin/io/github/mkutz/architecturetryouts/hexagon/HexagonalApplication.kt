package io.github.mkutz.architecturetryouts.hexagon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class HexagonalApplication

fun main(args: Array<String>) {
  runApplication<HexagonalApplication>(*args)
}
