package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}