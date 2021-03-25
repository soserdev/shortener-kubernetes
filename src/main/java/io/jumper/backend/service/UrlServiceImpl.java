package io.jumper.backend.service;

import io.jumper.backend.dto.UrlDto;
import io.jumper.backend.model.ShortUrl;
import io.jumper.backend.repository.UrlRepository;
import io.jumper.backend.util.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public String getUrl(String shortUrl) {
        var url = urlRepository.findByShortUrl(shortUrl);
        return url.getOriginalUrl();
    }

    @Override
    public String createUrl(String originalUrl) {
        var shortUrl = UrlShortener.create(originalUrl);
        var url = new ShortUrl();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        var savedUrl = urlRepository.save(url);
        return savedUrl.getShortUrl();
    }
}
