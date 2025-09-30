package io.jumper.api.repository;

import io.jumper.api.model.Url;
import org.springframework.beans.factory.annotation.Autowired;

// import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@EnabledIf(value = "#{environment.getActiveProfiles()[0] == 'mongodb-active'}", loadContext = true)
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    //@Test
    void findByShortUrl() {
        var url = new Url();
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