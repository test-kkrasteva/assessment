package com.sumup.technical.assessment.articles.controller;

import com.sumup.technical.assessment.articles.model.ArticleRequest;
import com.sumup.technical.assessment.articles.model.ArticleResponse;
import com.sumup.technical.assessment.articles.model.Error;
import com.sumup.technical.assessment.articles.service.ArticleProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("articles")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleProducerService producerService;

    @PostMapping("/createArticle")
    public ResponseEntity createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        ResponseEntity responseEntity = null;
        try {
            producerService.sendMessage(articleRequest.getGroup(), articleRequest);
            responseEntity =  ResponseEntity.status(HttpStatus.CREATED).body(
                    new ArticleResponse(articleRequest.getTitle(), articleRequest.getGroup(), articleRequest.getText()));
        } catch (Exception e) {
            logger.error("Failed to create article. " + e.getMessage(), e);
            responseEntity =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(e.getMessage()));
        }
        return responseEntity;
    }
}
