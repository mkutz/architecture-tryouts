package io.github.mkutz.architecturetryouts.hexagon

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false) class TestHexagonApplication

fun main(args: Array<String>) {
  fromApplication<HexagonalApplication>().with(TestHexagonApplication::class).run(*args)
}
