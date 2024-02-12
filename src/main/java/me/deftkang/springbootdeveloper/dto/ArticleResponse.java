package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import me.deftkang.springbootdeveloper.domain.Article;

import java.util.UUID;

@Getter
public class ArticleResponse {
    private final UUID id;
    private final String title;
    private final String content;
    private long modifiableDate;
    private String warningMessage;
    private String errorMessage;

    public ArticleResponse(Article article, long modifiableDate) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.modifiableDate = modifiableDate;
    }
    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
