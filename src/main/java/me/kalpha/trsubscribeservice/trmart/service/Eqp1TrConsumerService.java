package me.kalpha.trsubscribeservice.trmart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kalpha.trsubscribeservice.trmart.entity.Eqp1Tr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Eqp1TrConsumerService {
    @Autowired
    Eqp1TrService eqp1TrService;

    private KafkaTemplate<String, Eqp1Tr> eqp1TrKafkaTemplate;

    @Autowired
    Eqp1TrConsumerService(KafkaTemplate<String, Eqp1Tr> eqp1TrKafkaTemplate) {
        this.eqp1TrKafkaTemplate = eqp1TrKafkaTemplate;
    }
//    @KafkaListener(topics = "${app.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
//    public void receiveMessage(@Payload Eqp1Tr eqp1Tr,
//                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
//                               @Header(KafkaHeaders.OFFSET) Long offset) {
//        log.info("Received message: data = {}, topic = {}, partition = {}, offset = {}", eqp1Tr, topic, partition, offset);
//
//        eqp1TrService.createTr(eqp1Tr);
//    }
// https://github.com/thombergs/code-examples/blob/master/spring-boot/spring-boot-kafka/src/main/java/io/reflectoring/kafka/KafkaSenderExample.java
    void sendCustomMessage(Eqp1Tr eqp1Tr, String topicName) {
        log.info("Sending Json Serializer : {}", eqp1Tr);
        log.info("--------------------------------");

        eqp1TrKafkaTemplate.send(topicName, eqp1Tr);
    }

    void sendMessageWithCallback(String message, String topicName) {
        LOG.info("Sending : {}", message);
        LOG.info("---------------------------------");

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Success Callback: [{}] delivered with offset -{}", message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Failure Callback: Unable to deliver message [{}]. {}", message, ex.getMessage());
            }
        });
    }
}