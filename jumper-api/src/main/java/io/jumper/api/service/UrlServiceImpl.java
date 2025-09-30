package io.jumper.api.service;

import io.jumper.api.exception.SomethingWentWrongException;
import io.jumper.api.model.Url;
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
    public Optional<Url> getUrl(String shortUrl) {
        var url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            return Optional.empty();
        }
        return Optional.of(url);
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

    private Url createShortUrl(String originalUrl) {
        var shortUrl = UrlShortener.create(originalUrl);
        var url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        return url;
    }
}
