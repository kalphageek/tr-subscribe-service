package me.kalpha.trsubscribeservice.trmart.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaBindingListener implements ConsumerAwareRebalanceListener {
    private boolean initial = true;
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        log.info("# PartitionsAssigned");
        // 시작 시점에 대한 플래그가 없어서 필요 시 설정해야 함
        // 플래그 처리가 없으면 리밸런스 발생 시 항상 수행됨
        // 컨슈머가 여럿일 경우에는 토픽 파티션에 대한 구분 처리 필요
        if (initial) {
            // 오프셋 조정되어서 컨슈링 시작되지만, Consumer Group 오프셋 커밋이 되지 않음
            consumer.seekToBeginning(partitions);
            consumer.commitSync();
            //
            Map<TopicPartition, Long> topicPartitionMap = consumer.beginningOffsets(partitions);
            topicPartitionMap.forEach(consumer::seek);

            Map<TopicPartition, OffsetAndMetadata> topicOffsetMap = topicPartitionMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> new OffsetAndMetadata(e.getValue())));
            consumer.commitSync(topicOffsetMap);
            initial = false;
        }
    }
}
