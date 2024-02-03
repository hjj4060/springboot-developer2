package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import me.deftkang.springbootdeveloper.domain.Article;

import java.util.UUID;

@Getter
public class ArticleListViewResponse {
    private final UUID id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
