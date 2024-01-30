package me.deftkang.springbootdeveloper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.ArticleResponse;
import me.deftkang.springbootdeveloper.dto.UpdateArticleRequest;
import me.deftkang.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "블로그", description = "블로그 기본 CRUD api 입니다.")
@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    @Operation(summary = "블로그 글을 생성하는 API 입니다.")
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@Validated @RequestBody AddArticleRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //js에서 받은 데이터를 저장을 하고 저장한 데이터를 savedArticle 객체에 담는 기능
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @Operation(summary = "블로그 전체 글을 가져오는 API 입니다.")
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @Operation(summary = "블로그 한개 글을 가져오는 API 입니다.")
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable UUID id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @Operation(summary = "블로그 글을 삭제하는 API 입니다.")
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(summary = "블로그 글을 수정하는 API 입니다.")
    @PutMapping("api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable UUID id,
                                                 @RequestBody UpdateArticleRequest request) {

        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok().body(updatedArticle);
    }
}
