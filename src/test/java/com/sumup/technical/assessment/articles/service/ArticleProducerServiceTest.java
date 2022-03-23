package com.sumup.technical.assessment.articles.service;

import com.sumup.technical.assessment.articles.model.ArticleRequest;
import com.sumup.technical.assessment.articles.serialization.ArticleRequestDeserializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(topics = {"topic1"})
class ArticleProducerServiceTest {

    private static String TOPIC_NAME = "topic1";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ArticleProducerService articleProducerService;

    @Test
    public void itShould_ProduceCorrectExampleDTO_to_TOPIC_EXAMPLE_EXTERNE() {
        // give
        ArticleRequest articleRequest = new ArticleRequest("title1", "group1", "text1");

        // simulating consumer
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("group1", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ConsumerFactory cf = new DefaultKafkaConsumerFactory<String, ArticleRequest>(consumerProps, new StringDeserializer(), new ArticleRequestDeserializer());
        Consumer<String, ArticleRequest> consumerServiceTest = cf.createConsumer();

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumerServiceTest, TOPIC_NAME);

        // when
        articleProducerService.sendMessage(articleRequest.getGroup(), articleRequest);
        // then
        ConsumerRecord<String, ArticleRequest> consumerRecord = KafkaTestUtils.getSingleRecord(consumerServiceTest, TOPIC_NAME);

        assertEquals(articleRequest, consumerRecord.value());
        assertEquals(articleRequest.getGroup(), consumerRecord.key());

        consumerServiceTest.close();
    }
}