package io.jumper.backend.service;

import exception.SomethingWentWrongException;
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
        var url = createShortUrl(originalUrl);
        var found = urlRepository.findByShortUrl(url.getShortUrl());
        if (found != null) {
            throw new SomethingWentWrongException("Something went wrong while saving short url!");
        }
        var savedUrl = urlRepository.save(url);
        return savedUrl.getShortUrl();
    }

    private ShortUrl createShortUrl(String originalUrl) {
        var shortUrl = UrlShortener.create(originalUrl);
        var url = new ShortUrl();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        return url;
    }
}
