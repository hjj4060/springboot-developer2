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

    public List<Article> findAll(int createdOrderByFlag) {
        List<Article> findAllArticles = null;

        if (createdOrderByFlag == 1) {
            findAllArticles = blogRepository.findAllByOrderByCreatedAtAsc();
        } else {
            findAllArticles = blogRepository.findAllByOrderByCreatedAtDesc();
        }

        return findAllArticles;
    }

    @Transactional(readOnly = true)
    public Article findById(UUID id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(UUID id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(UUID id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        /**
         * 날짜 비교
         *  createdAt은 LocalDateTime 으로 받아옴
         *  LocalDateTime의 compareTo 함수는 비교하는 2개의 날짜가 같으면 시간까지 고려함
         *  그래서 LocalDate로 바꿔서 비교
         */
        LocalDate localDate = LocalDate.now();
        int diffDate = localDate.compareTo(article.getCreatedAt().toLocalDate());

        if (diffDate < 10) {
            article.update(request.getTitle(), request.getContent());
        }

        return article;
    }
}
