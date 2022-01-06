package io.jumper.api.repository;

import io.jumper.api.model.ShortUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @Test
    void findByShortUrl() {
        var url = new ShortUrl();
        var shortUrl = "abc123";
        url.setOriginalUrl("https//www.google.com");
        url.setShortUrl(shortUrl);
        var savedUrl = urlRepository.save(url);
        System.out.println(savedUrl.getId());
        System.out.println(savedUrl.getShortUrl());
        System.out.println(savedUrl.getOriginalUrl());

        var foundUrl = urlRepository.findByShortUrl(savedUrl.getShortUrl());
        System.out.println(foundUrl.getId());
        System.out.println(foundUrl.getShortUrl());
        System.out.println(foundUrl.getOriginalUrl());

        urlRepository.deleteById(savedUrl.getId());
    }
}