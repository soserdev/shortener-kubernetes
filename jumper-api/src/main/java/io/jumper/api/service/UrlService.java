package io.jumper.api.service;

import java.util.Optional;

public interface UrlService {

    Optional<String> getUrl(final String shortUrl);
    Optional<String> createUrl(final String url);

}
