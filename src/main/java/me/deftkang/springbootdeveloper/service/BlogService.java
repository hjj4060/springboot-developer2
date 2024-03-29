package me.deftkang.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.ArticleResponse;
import me.deftkang.springbootdeveloper.dto.UpdateArticleRequest;
import me.deftkang.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    //article 테이블에 저장되어 있는 모든 데이터 조회
    //findAll() 함수는 Entity객체를 담은 List가 반환됨
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public List<ArticleResponse> findAll(int createdOrderByFlag, String title) {
        List<Article> findAllArticles = null;

        if(title == null) {
            if (createdOrderByFlag == 1) {
//                findAllArticles = blogRepository.findAllByOrderByCreatedAtAsc();
                findAllArticles = blogRepository.findAllByDeletedAtIsNullOrderByCreatedAtAsc();
            } else {
//                findAllArticles = blogRepository.findAllByOrderByCreatedAtDesc();
                findAllArticles = blogRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();
            }
        } else {
            if (createdOrderByFlag == 1) {
//                findAllArticles = blogRepository.findAllByOrderByCreatedAtAscAndTitle(title);
                findAllArticles = blogRepository.findAllByDeletedAtIsNullOrderByCreatedAtAscAndTitle(title);
            } else {
//                findAllArticles = blogRepository.findAllByOrderByCreatedAtDescAndTitle(title);
                findAllArticles = blogRepository.findAllByDeletedAtIsNullOrderByCreatedAtDescAndTitle(title);
            }
        }

        return findAllArticles.stream().map(article -> {
            long modifiableDate = article.calculateModifiableDate(article.getCreatedAt());
            return new ArticleResponse(article, modifiableDate);
        }).toList();
    }

    @Transactional(readOnly = true)
    public ArticleResponse findById(UUID id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        long modifiableDate = article.calculateModifiableDate(article.getCreatedAt());

        return new ArticleResponse(article, modifiableDate);
    }

    public void delete(UUID id) {
        blogRepository.deleteById(id);
    }

    //@transactional 이 없으면 엔티티 수정시 업데이트가 되지않는다.
    @Transactional
    //deltedAt을 업데이트 하지만 DTO를 사용하지 않음
    public Article updateDeleteAt(UUID id) {
        Article article = articleValidCheck(id);
        article.setDeletedAt(LocalDateTime.now());

        return article;
    }

    @Transactional
    public ArticleResponse update(UUID id, UpdateArticleRequest request) {
        Article article = articleValidCheck(id);
        long modifiableDate = article.calculateModifiableDate(article.getCreatedAt());
        ArticleResponse articleResponse = new ArticleResponse(article, modifiableDate);

        if (articleResponse.getModifiableDate() <= 0) {
            throw new IllegalStateException("게시물 수정 기간이 지났습니다.");
        }

        if (articleResponse.getModifiableDate() == 1) {
            articleResponse.setWarningMessage("하루가 지나면 수정하지 못합니다.");
        }

        article.update(request.getTitle(), request.getContent());

        return articleResponse;
    }

    //test 용
    @Transactional
    public ArticleResponse update(UUID id, UpdateArticleRequest request, LocalDateTime createdDateAt) {
        Article article = articleValidCheck(id);
        long modifiableDate = article.calculateModifiableDate(createdDateAt);
        ArticleResponse articleResponse = new ArticleResponse(article, modifiableDate);

        if (articleResponse.getModifiableDate() <= 0) {
            throw new IllegalStateException("게시물 수정 기간이 지났습니다.");
        }

        if (articleResponse.getModifiableDate() == 1) {
            articleResponse.setWarningMessage("하루가 지나면 수정하지 못합니다.");
        }

        article.update(request.getTitle(), request.getContent());

        return articleResponse;
    }

    private Article articleValidCheck(UUID id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return article;
    }
}
