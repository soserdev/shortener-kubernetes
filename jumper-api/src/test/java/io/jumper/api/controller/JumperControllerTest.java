package io.jumper.api.controller;

import io.jumper.api.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JumperController.class)
class JumperControllerTest {

    public static final String LOCATION = "Location";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UrlService urlService;

    @Test
    void redirectNotFound() throws Exception {
        var shortUrl = "abc123";
        given(urlService.getUrl(any())).willReturn(Optional.empty());
        mockMvc.perform(get("/" + shortUrl))
                .andExpect(status().isNotFound());
    }

    @Test
    void redirect() throws Exception {
        var shortUrl = "abc123";
        var originalUrl = "http://google.com";
        given(urlService.getUrl(any())).willReturn(Optional.of(originalUrl));
        mockMvc.perform(get("/" + shortUrl))
                .andExpect(status().isFound())
                .andExpect(header().exists(LOCATION))
                .andExpect(header().string(LOCATION, containsString(originalUrl)));
    }

    @Test
    void getOriginalUrl() throws Exception {
        var shortUrl = "abc123";
        var originalUrl = "http://google.com";
        given(urlService.getUrl(any())).willReturn(Optional.of(originalUrl));
        mockMvc.perform(get("/shorturl/" + shortUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.url", is(originalUrl)))
                .andExpect(jsonPath("$.shortUrl", is(shortUrl)));
    }

    @Test
    void getOriginalUrlNotFound() throws Exception {
        var shortUrl = "abc123";
        given(urlService.getUrl(any())).willReturn(Optional.empty());
        mockMvc.perform(get("/shorturl/" + shortUrl))
                .andExpect(status().isNotFound());
    }

}