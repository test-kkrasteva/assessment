package com.sumup.technical.assessment.articles.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.input.topic.name}")
    private String inputTopic;

    @Value(value = "${kafka.input.topic.partitions}")
    private String inputNumPartitions;

    @Value(value = "${kafka.input.topic.replication.factor}")
    private String inputReplicationFactor;

    @Value(value = "${kafka.output.topic.name}")
    private String outputTopic;

    @Value(value = "${kafka.output.topic.partitions}")
    private String outputNumPartitions;

    @Value(value = "${kafka.output.topic.replication.factor}")
    private String outputReplicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicAllMessages() {
        return new NewTopic(inputTopic, Integer.parseInt(inputNumPartitions), Short.parseShort(inputReplicationFactor));
    }

    @Bean
    public NewTopic topicFilteredMessages() {
        return new NewTopic(outputTopic, Integer.parseInt(outputNumPartitions), Short.parseShort(outputReplicationFactor));
    }
}
