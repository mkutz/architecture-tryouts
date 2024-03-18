package io.github.mkutz.architecturetryouts.layered

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class LayeredApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<LayeredApplication>(*args)
}
