package com.panov.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.service.UrlServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlServiceImpl urlService;

    @Test
    void findAllUrls() throws Exception {
        Url url = Url.builder().id(1L).url("https://www.google.com/").urlShort("test").urlGenerate("test").build();
        Url url1 = Url.builder().id(2L).url("https://ya.ru/").urlShort("demo").urlGenerate("demo").build();
        List<Url> urls = new ArrayList<>(Arrays.asList(url, url1));

        given(urlService.findAllUrl()).willReturn(urls);

        mockMvc
                .perform(
                        get("/api/v1/urls")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void createShortLink() throws Exception {
        Url url = Url.builder().id(1L).url("https://www.google.com/").urlShort("test").urlGenerate("test").build();
        UrlDto urlDto = new UrlDto("https://www.google.com/", "test");
        given(urlService.generateShortLink(urlDto)).willReturn(url);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(urlDto);


        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/urls")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void redirectToShortLink() throws Exception {
        Url url = Url.builder().id(1L).url("https://www.google.com/").urlShort("test").urlGenerate("test").build();
        given(urlService.findUrlByShortLink("test")).willReturn(url);

        mockMvc
                .perform(
                        get("/api/v1/urls/test")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1));


    }
}