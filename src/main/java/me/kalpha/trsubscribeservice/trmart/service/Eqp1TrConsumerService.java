package me.kalpha.trsubscribeservice.trmart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kalpha.trsubscribeservice.trmart.entity.Eqp1Tr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Eqp1TrConsumerService {
    @Autowired
    Eqp1TrService eqp1TrService;

    @KafkaListener(topics = "${app.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(@Payload Eqp1Tr eqp1Tr,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                               @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Received message: data = {}, topic = {}, partition = {}, offset = {}", eqp1Tr, topic, partition, offset);

        eqp1TrService.createTr(eqp1Tr);
    }
//    @KafkaListener(topics = "${app.topic.name}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "eqp1TrKafkaListenerContainerFactory")
//    public void onMessage(ConsumerRecords<String, Eqp1Tr> consumerRecords) {
//        StreamSupport.stream(consumerRecords.spliterator(), false)
//                .map(ConsumerRecord::value)
//                .filter(Objects::nonNull)
//                .forEach(eqp1TrService::createTr);
//        log.info("consumed count: {}", consumerRecords.count());
//    }
}