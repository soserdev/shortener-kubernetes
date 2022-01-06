package io.jumper.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerTest {

    @Test
    void create() {
        var originalUrl = "https://google.com";
        var shortUrl = UrlShortener.create(originalUrl);
        assertNotNull(shortUrl);
        assertTrue(shortUrl.length() == 6, "Length should be 6");
    }

    @Test
    void createUnique() {
        // create should generate different shrt urls
        var originalUrl = "https://google.com";
        var s1 = UrlShortener.create(originalUrl);
        var s2 = UrlShortener.create(originalUrl);
        assertNotNull(s1);
        assertNotNull(s2);
        assertTrue(s1.length() == 6, "Length should be 6");
        assertTrue(s2.length() == 6, "Length should be 6");
        assertNotEquals(s1.equals(s2), "shorturl:" + s1 + " should not equal shorturl:" + s2 );
    }
}