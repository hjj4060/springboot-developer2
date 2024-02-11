package me.deftkang.springbootdeveloper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.ArticleResponse;
import me.deftkang.springbootdeveloper.dto.ArticleViewResponse;
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

    @Operation(summary = "블로그 전체 글을 가져오는 API 입니다. 정렬 기능이 있습니다. 1이 오름차순, 2가 내림차순")
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(
            @RequestParam(name = "createdOrder", defaultValue = "1") int createdOrder,
            @RequestParam(name = "title", required = false) String title) {
        //엔티티를 DTO로 변경하여 리스트에 담는 작업
        List<ArticleResponse> articles = blogService.findAll(createdOrder, title);

        return ResponseEntity.ok()
                .body(articles);
    }

    @Operation(summary = "블로그 한개 글을 가져오는 API 입니다.")
    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable UUID id) {
        ArticleResponse article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(article);
    }

    @Operation(summary = "블로그 글을 삭제하는 API 입니다.")
    @DeleteMapping("api/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(summary = "블로그 글을 삭제 날짜를 업데이트 하는 API 입니다.")
    @PatchMapping("api/article/{id}") //Article의 deltedAt 필드만 수정하는거여서 PatchMapping 사용
    public ResponseEntity<Void> updateArticleDeleteAt(@PathVariable UUID id) {
        Article article = blogService.updateDeleteAt(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(summary = "블로그 글을 수정하는 API 입니다.")
    @PutMapping("api/article/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable UUID id,
                                                 @RequestBody UpdateArticleRequest request) {
        try {
            Article updatedArticle = blogService.update(id, request);
            return ResponseEntity.ok().body(updatedArticle);
            //Response 객체 만들어서 컬럼 추가해서 수정가능한지 알림 보내기
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
