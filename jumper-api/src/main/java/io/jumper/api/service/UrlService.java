package io.jumper.api.service;

import io.jumper.api.model.Url;

import java.util.Optional;

public interface UrlService {

    Optional<Url> getUrl(final String shortUrl);
    Optional<String> createUrl(final String url);

}
