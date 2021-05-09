package me.kalpha.trsubscribeservice.trmart.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableKafka
@Component
public class KafkaConsumerConfig {
    private final MessageConverter messageConverter;
    private final KafkaBindingListener kafkaBindingListener;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(trKafkaConsumerFactory());
        factory.setMessageConverter(messageConverter);
        ContainerProperties containerProperties = factory.getContainerProperties();
        // 레코드 당 커밋 모드(accnowledgement.acknowledge() 바로 반영)
        // 매번 호출 시 처리 느림
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        // 배치 커밋 모드(accnowledgement.acknowledge() 하더라도 모아서 처리하므로 바로 반영 안될 수 있음)
        // 매번 호춣 하더라도 모아서 처리하므로 크게 상관없음
//        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL);
        containerProperties.setConsumerRebalanceListener(kafkaBindingListener);
        return factory;
    }

    @Bean
    public ConsumerFactory<? super String,? super byte[]> trKafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
