package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import me.deftkang.springbootdeveloper.api.ArticleAPI;
import me.deftkang.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;
    private long modifiableDate;

    public ArticleResponse(Article article, long modifiableDate) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.modifiableDate = modifiableDate;
    }
}
