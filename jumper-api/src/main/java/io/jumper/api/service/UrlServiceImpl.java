package io.jumper.api.service;

import io.jumper.api.exception.SomethingWentWrongException;
import io.jumper.api.model.ShortUrl;
import io.jumper.api.repository.UrlRepository;
import io.jumper.api.util.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Optional<String> getUrl(String shortUrl) {
        var url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            return Optional.empty();
        }
        return Optional.of(url.getOriginalUrl());
    }

    @Override
    public Optional<String> createUrl(String originalUrl) {
        var url = createShortUrl(originalUrl);
        var found = urlRepository.findByShortUrl(url.getShortUrl());
        if (found != null) {
            throw new SomethingWentWrongException("Short url is not unique!");
        }
        var savedUrl = urlRepository.save(url);
        return Optional.of(savedUrl.getShortUrl());
    }

    private ShortUrl createShortUrl(String originalUrl) {
        var shortUrl = UrlShortener.create(originalUrl);
        var url = new ShortUrl();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        return url;
    }
}
