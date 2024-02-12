package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.deftkang.springbootdeveloper.api.ArticleAPI;
import me.deftkang.springbootdeveloper.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long modifiableDate;

    public ArticleViewResponse(Article article, long modifiableDate) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.modifiableDate = modifiableDate;
    }
}