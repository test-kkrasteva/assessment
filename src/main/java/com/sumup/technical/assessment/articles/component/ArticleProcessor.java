package com.sumup.technical.assessment.articles.component;

import com.sumup.technical.assessment.articles.model.ArticleRequest;
import com.sumup.technical.assessment.articles.serialization.ArticleRequestDeserializer;
import com.sumup.technical.assessment.articles.serialization.ArticleRequestSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ArticleProcessor {

    private final String[] words = {"Welcome", "to", "SumUp", "coding", "challenge"};

    @Value(value = "${kafka.input.topic.name}")
    private String inputTopic;

    @Value(value = "${kafka.output.topic.name}")
    private String outputTopic;

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        final Serializer<ArticleRequest> ser = new ArticleRequestSerializer();
        final Deserializer<ArticleRequest> des = new ArticleRequestDeserializer();
        final Serde<ArticleRequest> articleSerde = Serdes.serdeFrom(ser, des);

        streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), articleSerde))
                .filter((key, article) -> Arrays.stream(words).map(word -> word.toLowerCase()).anyMatch(article.getText().toLowerCase()::contains))
                .to(outputTopic, Produced.with(Serdes.String(), articleSerde));
    }
}

