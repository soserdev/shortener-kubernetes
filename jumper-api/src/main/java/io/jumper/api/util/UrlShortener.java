package io.jumper.api.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UrlShortener {

    public static String create(final String url) {
        if (url == null) throw new IllegalArgumentException();

        var stringToEncode = url + System.currentTimeMillis();
        var md5DigestAsHex = DigestUtils.md5DigestAsHex(stringToEncode.getBytes(StandardCharsets.UTF_8));
        var digest = Base64.getUrlEncoder()
                .encodeToString(md5DigestAsHex.getBytes(StandardCharsets.UTF_8))
                .toString()
                .replace('=', 'x')
                .replace('\\', 'n');
        return digest.substring(0,6);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        var url = "https://www.heise.de";
        System.out.printf("Url: '%s' \t Shorturl: '%s'%n", url, create(url));
        System.out.printf("Url: '%s' \t Shorturl: '%s'%n", url, create(url));
    }
}
