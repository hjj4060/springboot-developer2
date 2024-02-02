package me.deftkang.springbootdeveloper.repository;

import me.deftkang.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Article, UUID> {

    //createdAt 기준으로 오름차순 정렬
    List<Article> findAllByOrderByCreatedAtAsc();

    //createdAt 기준으로 내림차순 정렬
    List<Article> findAllByOrderByCreatedAtDesc();
}


