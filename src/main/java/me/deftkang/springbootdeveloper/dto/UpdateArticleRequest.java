package me.deftkang.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    private LocalDateTime deletedAt;

    public UpdateArticleRequest(String title, String content) {
        this(title, content, null);
    }
}
