package io.jumper.backend.service;

public interface UrlService {

    String getUrl(final String shortUrl);
    String createUrl(final String url);

}
