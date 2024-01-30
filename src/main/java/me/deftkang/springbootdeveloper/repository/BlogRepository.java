package me.deftkang.springbootdeveloper.repository;

import me.deftkang.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogRepository extends JpaRepository<Article, UUID> {

}


