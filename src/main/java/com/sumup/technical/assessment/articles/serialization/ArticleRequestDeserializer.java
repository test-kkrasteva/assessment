package com.sumup.technical.assessment.articles.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumup.technical.assessment.articles.model.ArticleRequest;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ArticleRequestDeserializer implements Deserializer<ArticleRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public ArticleRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                return null;
            }
            return objectMapper.readValue(new String(data, "UTF-8"), ArticleRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to ArticleRequest");
        }
    }

    @Override
    public void close() {
    }
}
