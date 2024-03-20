package io.github.mkutz.architecturetryouts.layered.rating

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Service

@KafkaListener(topics = ["ratings"])
class RatingConsumer(private val ratingService: RatingService) : MessageListener<String, RatingMessage> {
  
  override fun onMessage(consumerRecord: ConsumerRecord<String, RatingMessage>) {
    ratingService.register(Rating(consumerRecord.value()))
  }
}
