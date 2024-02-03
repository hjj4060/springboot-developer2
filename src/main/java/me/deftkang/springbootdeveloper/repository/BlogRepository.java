package me.deftkang.springbootdeveloper.repository;

import me.deftkang.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Article, UUID> {

    //createdAt 기준으로 오름차순 정렬
    List<Article> findAllByOrderByCreatedAtAsc();

    //deletedAt 필드가 null인것과 createdAt 기준으로 오름차순 정렬
    List<Article> findAllByDeletedAtIsNullOrderByCreatedAtAsc();

    //createdAt 기준으로 내림차순 정렬
    List<Article> findAllByOrderByCreatedAtDesc();

    //deletedAt 필드가 null인것과 createdAt 기준으로 내림차순 정렬
    List<Article> findAllByDeletedAtIsNullOrderByCreatedAtDesc();

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:title% ORDER BY a.createdAt")
    List<Article> findAllByOrderByCreatedAtAscAndTitle(@Param("title") String title);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:title% ORDER BY a.createdAt DESC")
    List<Article> findAllByOrderByCreatedAtDescAndTitle(@Param("title") String title);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:title% AND a.deletedAt IS NULL ORDER BY a.createdAt")
    List<Article> findAllByDeletedAtIsNullOrderByCreatedAtAscAndTitle(@Param("title") String title);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:title% AND a.deletedAt IS NULL ORDER BY a.createdAt DESC")
    List<Article> findAllByDeletedAtIsNullOrderByCreatedAtDescAndTitle(@Param("title") String title);
}


