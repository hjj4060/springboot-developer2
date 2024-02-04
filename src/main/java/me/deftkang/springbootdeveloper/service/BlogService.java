package me.deftkang.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.UpdateArticleRequest;
import me.deftkang.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

    public List<Article> findAll(int createdOrderByFlag, String title) {
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

        return findAllArticles;
    }

    @Transactional(readOnly = true) //readOnly 옵션 true로 하면 락을 안건다??
    public Article findById(UUID id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
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
    public Article update(UUID id, UpdateArticleRequest request) {
        Article article = articleValidCheck(id);

        LocalDate localDate = LocalDate.now();
        long diffDate = ChronoUnit.DAYS.between(article.getCreatedAt().toLocalDate(), localDate);

        if (diffDate >= 10) {
            throw new IllegalStateException("게시물 수정 기간이 지났습니다.");
        }

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    private Article articleValidCheck(UUID id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return article;
    }
}
