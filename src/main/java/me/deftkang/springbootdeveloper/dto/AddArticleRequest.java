package me.deftkang.springbootdeveloper.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.deftkang.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    @Size(max = 199)
    private String title;

    @Pattern(regexp = "^.{0,999}$")
    private String content;

    //DTO -> Entity 클래스로 변환 JPA Repository의 로직을 수행시키기 위해서
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
