package io.github.mkutz.architecturetryouts.layered.architecture

import java.util.concurrent.TimeUnit.SECONDS
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service

private const val TIMEOUT = 10L

@Service
class ArchitectureProducer(private val kafkaTemplate: KafkaTemplate<String, ArchitectureMessage>) {

  fun publish(message: ArchitectureMessage): SendResult<String, ArchitectureMessage> =
    kafkaTemplate.send("architecture", message.id, message).get(TIMEOUT, SECONDS)
}
