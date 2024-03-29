package me.deftkang.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.deftkang.springbootdeveloper.domain.Article;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import me.deftkang.springbootdeveloper.dto.ArticleViewResponse;
import me.deftkang.springbootdeveloper.dto.UpdateArticleRequest;
import me.deftkang.springbootdeveloper.repository.BlogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 블로그 글 추가에 대한 유효성 검사
 */
@SpringBootTest //통합테스트
@AutoConfigureMockMvc
public class BlogArticleValidTest {

    @Autowired
    private MockMvc mockMvc;

    //Object 객체를 Json 형식으로 변경해줌
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @Test
    @DisplayName("블로그 title글자 수는 200자 보다 작아야 한다.")
    void articleTitleValid_O() throws Exception {
        final String url = "/api/articles";
        final String title = "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자" +
                "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자" +
                "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자0000000";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        Assertions.assertThat(title.length()).isLessThan(200);
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("블로그 title글자 수는 200자 이상 되면 안된다.")
    void articleTitleValid_X() throws Exception {
        final String url = "/api/articles";
        final String title = "a".repeat(200);
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        Assertions.assertThat(title.length()).isEqualTo(200);
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("블로그 content 글자 수는 1000자 보다 작아야 한다.")
    void articleContentValid_O() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "a".repeat(999);
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        Assertions.assertThat(content.length()).isLessThan(1000);
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("블로그 content 글자 수는 1000자 이상 되면 안된다.")
    void articleContentValid_X() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "a".repeat(1000);
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        Assertions.assertThat(content.length()).isEqualTo(1000);
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("모든 글 조회시 title 입력 조건 체크")
    void blogFindAllTitleValid_O() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        ResultActions resultActions = mockMvc.perform(get(url)
                .param("title", "tl")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("모든 글 조회시 title 입력 조건 체크")
    void blogFindAllTitleValid_X() throws Exception {
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        ResultActions resultActions = mockMvc.perform(get(url)
                .param("title", "al")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("글 전체 조회 할시 deletedAt 필드에 데이터가 있으면 조회되면 안됩니다.")
    public void blogFindAllExcludeDeletedIsNull() throws Exception {
        //given
        final String forUpdateDeletedAtUrl = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        //deletedAt 필드 업데이트
        ResultActions resultActions = mockMvc.perform(patch(forUpdateDeletedAtUrl, savedArticle.getId()))
                .andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        Assertions.assertThat(article.getDeletedAt()).isNotNull(); //deleteAt이 정상적으로 들어갔는지 확인

        final String forFindAllUrl = "/api/articles";
        ResultActions resultActions2 = mockMvc.perform(get(forFindAllUrl)
                .param("createdOrder", "2")
                .param("title", "ti")
                .accept(MediaType.APPLICATION_JSON));

        //then
        //모든 글 조회시 삭제된건 조회되면 안된다.
        resultActions2.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("글 상세 조회시 생성일이 9일 됐을 때 수정 가능한 일 1로 보여준다.")
    public void remainingModifiableDateValid1() throws Exception {
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        UpdateArticleRequest request = new UpdateArticleRequest("new title", "new content");

        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        //when
        savedArticle.setCreatedAt(LocalDateTime.now().minusDays(9)); //9일전에 생성
        long modifiableDate = savedArticle.calculateModifiableDate(savedArticle.getCreatedAt());
        ArticleViewResponse response = new ArticleViewResponse(savedArticle, modifiableDate);

        //then
        Assertions.assertThat(response.getModifiableDate()).isEqualTo(1);
    }

    @Test
    @DisplayName("글 상세 조회시 생성일이 10일이 지났을 때 됐을 때 수정 가능한 일 0으로 보여준다.")
    public void remainingModifiableDateValid2() throws Exception {
        //given
        final String url = "/api/article/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        UpdateArticleRequest request = new UpdateArticleRequest("new title", "new content");

        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        savedArticle.setCreatedAt(LocalDateTime.now().minusDays(15)); //15일전에 생성

        //when
        long modifiableDate = savedArticle.calculateModifiableDate(savedArticle.getCreatedAt());
        ArticleViewResponse response = new ArticleViewResponse(savedArticle, modifiableDate);

        //then
        Assertions.assertThat(response.getModifiableDate()).isEqualTo(0);
    }
}