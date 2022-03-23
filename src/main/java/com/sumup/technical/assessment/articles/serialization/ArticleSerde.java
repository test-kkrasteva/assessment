package com.sumup.technical.assessment.articles.serialization;

import com.sumup.technical.assessment.articles.model.ArticleRequest;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class ArticleSerde implements Serde<ArticleRequest> {

    @Override
    public Serializer<ArticleRequest> serializer() {
        return new ArticleRequestSerializer();
    }

    @Override
    public Deserializer<ArticleRequest> deserializer() {
        return new ArticleRequestDeserializer();
    }
}
