package io.jumper.api.service;

import io.jumper.api.model.Url;
import io.jumper.api.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    UrlRepository urlRepository;

    @Test
    void getUrlNotFound() {
        var urlService = new UrlServiceImpl(urlRepository);
        when(urlRepository.findByShortUrl(any())).thenReturn(null);
        Optional<Url> originalUrl = urlService.getUrl("abcd1234");
        assertEquals(Optional.empty(), originalUrl);
    }

    @Test
    void getUrlFound() {
        var urlService = new UrlServiceImpl(urlRepository);
        var shortUrl = "abcd1234";
        var originalUrl = "http://www.google.com";
        when(urlRepository.findByShortUrl(any()))
                .thenReturn(new Url(UUID.randomUUID().toString(), shortUrl, originalUrl));
        Optional<Url> originalUrlRetrieved = urlService.getUrl(shortUrl);
        assertNotEquals(Optional.empty(), originalUrlRetrieved);
        assertEquals(shortUrl, originalUrlRetrieved.get().getShortUrl());
        assertEquals(originalUrl, originalUrlRetrieved.get().getOriginalUrl());
    }

}