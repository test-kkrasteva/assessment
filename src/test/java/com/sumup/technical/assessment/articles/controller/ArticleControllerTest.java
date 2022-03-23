package com.sumup.technical.assessment.articles.controller;

import com.sumup.technical.assessment.articles.model.ArticleResponse;
import com.sumup.technical.assessment.articles.model.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ArticleControllerTest {

    @InjectMocks
    ArticleController articleController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void createArticle() throws Exception {
        String title = "Galapagos Islands";
        String group = "travel";
        String text = "The Galapagos archipelago is located about 1,000 km from continental Ecuador and is composed of 127 islands";
        String payload = "{\"title\":\"" + title + "\"group\":\"" + group + "\"text\":\"" + text + "\"}";

        ArticleResponse articleResponse = new ArticleResponse(title, group, text);

        mockMvc.perform(post("/articles/createArticle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> new ResponseEntity(articleResponse, HttpStatus.CREATED));
    }

    @Test
    void createArticleFailedTitleValidaiton() throws Exception {
        String title = null;
        String group = "travel";
        String text = "The Galapagos archipelago is located about 1,000 km from continental Ecuador and is composed of 127 islands";
        String payload = "{\"title\":\"" + title + "\"group\":\"" + group + "\"text\":\"" + text + "\"}";

        mockMvc.perform(post("/articles/createArticle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> new ResponseEntity(new Error("Title is mandatory"), HttpStatus.BAD_REQUEST));
    }

    @Test
    void createArticleFailedGroupValidation() throws Exception {
        String title = "Galapagos Islands";
        String group = "trav";
        String text = "The Galapagos archipelago is located about 1,000 km from continental Ecuador and is composed of 127 islands";
        String payload = "{\"title\":\"" + title + "\"group\":\"" + group + "\"text\":\"" + text + "\"}";

        mockMvc.perform(post("/articles/createArticle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> new ResponseEntity(new Error("Group must be between 5 and 10 characters"), HttpStatus.BAD_REQUEST));
    }

    @Test
    void createArticleFailedTextValidation() throws Exception {
        String title = "null";
        String group = "travel";
        String text = "";
        String payload = "{\"title\":\"" + title + "\"group\":\"" + group + "\"text\":\"" + text + "\"}";

        mockMvc.perform(post("/articles/createArticle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> new ResponseEntity(new Error("Text must be between 1 and 1000 characters"), HttpStatus.BAD_REQUEST));
    }
}