package io.jumper.api.service;

import io.jumper.api.model.ShortUrl;
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
        Optional<String> originalUrl = urlService.getUrl("abcd1234");
        assertEquals(Optional.empty(), originalUrl);
    }

    @Test
    void getUrlFound() {
        var urlService = new UrlServiceImpl(urlRepository);
        var shortUrl = "abcd1234";
        var originalUrl = "http://www.google.com";
        when(urlRepository.findByShortUrl(any()))
                .thenReturn(new ShortUrl(UUID.randomUUID().toString(), shortUrl, originalUrl));
        var originalUrlRetrieved = urlService.getUrl(shortUrl);
        assertNotEquals(Optional.empty(), originalUrlRetrieved);
        assertEquals(originalUrl, originalUrlRetrieved.get());
    }

}