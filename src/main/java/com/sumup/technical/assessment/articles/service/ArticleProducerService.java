package com.sumup.technical.assessment.articles.service;

import com.sumup.technical.assessment.articles.model.ArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleProducerService {

    @Value(value = "${kafka.input.topic.name}")
    private String inputTopic;

    @Autowired
    KafkaTemplate<String, ArticleRequest> kafkaTemplate;

    public void sendMessage(String key, ArticleRequest value) {
        kafkaTemplate.send(inputTopic, key, value);
    }
}
