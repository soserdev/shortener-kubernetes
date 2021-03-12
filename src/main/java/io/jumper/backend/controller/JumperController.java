package io.jumper.backend.controller;

import io.jumper.backend.model.ShortUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class JumperController {

    @GetMapping("/{shortUrl:[\\d]+}")
    public ResponseEntity<ShortUrl> get(@PathVariable("shortUrl") String shortUrlPath) {
        var body = ShortUrl.builder().shortUrl("" + shortUrlPath).destinationUrl("https://www.heise.de").build();
        return new ResponseEntity<ShortUrl>(body, HttpStatus.OK);
    }

}
