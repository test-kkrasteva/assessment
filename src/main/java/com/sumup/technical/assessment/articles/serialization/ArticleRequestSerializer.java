package com.sumup.technical.assessment.articles.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumup.technical.assessment.articles.model.ArticleRequest;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ArticleRequestSerializer implements Serializer<ArticleRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, ArticleRequest data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing ArticleRequest to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
