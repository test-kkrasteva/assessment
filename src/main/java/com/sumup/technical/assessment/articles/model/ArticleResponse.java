package com.sumup.technical.assessment.articles.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ArticleResponse {

    @NotNull(message = "Title is mandatory")
    @Size(min = 3, max = 17, message = "Title must be between 3 and 17 characters")
    private String title;

    @NotNull(message = "Group is mandatory")
    @Size(min = 5, max = 10, message = "Group must be between 5 and 10 characters")
    private String group;

    @NotNull(message = "Text is mandatory")
    @Size(min = 1, max = 1000, message = "The text must be between 1 and 1000 characters")
    private String text;

    public ArticleResponse() {
    }

    public ArticleResponse(String title, String group, String text) {
        this.title = title;
        this.group = group;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "title='" + title + '\'' +
                ", group='" + group + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
