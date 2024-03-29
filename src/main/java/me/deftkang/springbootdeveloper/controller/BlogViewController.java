package me.deftkang.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.ArticleListViewResponse;
import me.deftkang.springbootdeveloper.dto.ArticleResponse;
import me.deftkang.springbootdeveloper.dto.ArticleViewResponse;
import me.deftkang.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(
            @RequestParam(name = "createdOrder", defaultValue = "1") int createdOrder,
            @RequestParam(name = "title", required = false) String title
            , Model model) {
        List<ArticleResponse> articles = blogService.findAll(createdOrder, title);

        model.addAttribute("articles", articles);
        
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable UUID id, Model model) {
        ArticleResponse articleResponse = blogService.findById(id);
        model.addAttribute("article", articleResponse);

        return "article";
    }

    @GetMapping("/new-articles")
    public String newArticle(@RequestParam(required = false) UUID id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            ArticleResponse articleResponse = blogService.findById(id);
            model.addAttribute("article", articleResponse);
        }

        return "newArticle";
    }
}
