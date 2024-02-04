package me.deftkang.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long modifiableDate;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.modifiableDate = calculateModifiableDate(article.getCreatedAt());
    }

    public long calculateModifiableDate(LocalDateTime createdDatedAt) {
        LocalDate localDate = LocalDate.now();
        long diffDate = ChronoUnit.DAYS.between(createdDatedAt.toLocalDate(), localDate);

        return 10 - diffDate;
    }
}