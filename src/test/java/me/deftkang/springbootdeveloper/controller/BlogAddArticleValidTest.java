package me.deftkang.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.deftkang.springbootdeveloper.dto.AddArticleRequest;
import org.assertj.core.api.Assertions;
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

/**
 * 블로그 글 추가에 대한 유효성 검사
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BlogAddArticleValidTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

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
        final String title = "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자" +
                "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자" +
                "200자200자200자200자200자200자200자200자200자200자200자200자200자200자200자00000000";
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
        final String content = "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수" +
                "체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체";
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
        final String content = "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수" +
                "체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크" +
                "글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자" +
                "수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체" +
                "크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글자수체크글" +
                "자수체크글자수체크글자수체크글자수체크글자수체크글자수체크";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)
        );

        Assertions.assertThat(content.length()).isEqualTo(1000);
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}