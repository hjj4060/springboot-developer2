package me.deftkang.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.UpdateArticleRequest;
import me.deftkang.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    //article 테이블에 저장되어 있는 모든 데이터 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
