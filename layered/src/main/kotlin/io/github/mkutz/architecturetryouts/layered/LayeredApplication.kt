package io.github.mkutz.architecturetryouts.layered

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder

@SpringBootApplication @EnableKafka class LayeredApplication

@Bean fun ratingTopic() = TopicBuilder.name("rating_v1").build()

@Bean fun architectureTopic() = TopicBuilder.name("architecture_v2").build()

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<LayeredApplication>(*args)
}
