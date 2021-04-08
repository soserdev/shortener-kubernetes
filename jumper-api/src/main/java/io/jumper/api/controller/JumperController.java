package io.jumper.api.controller;

import io.jumper.api.dto.UrlDto;
import io.jumper.api.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController()
public class JumperController {

    @Autowired
    private UrlService urlService;

    // curl localhost:8080
    @GetMapping("/shorturl/{shortUrl:[a-zA-Z0-9]{6}}")
    public ResponseEntity<UrlDto> get(@PathVariable("shortUrl") String shortUrlPath) {
        var originalUrl = urlService.getUrl(shortUrlPath);
        var urlDto = UrlDto.builder()
                .shortUrl(shortUrlPath)
                .url(originalUrl)
                .build();
        return new ResponseEntity<UrlDto>(urlDto, HttpStatus.OK);
    }

    // curl -v -H'Content-Type: application/json' -d'{"url": "http://www.swr3.de"}' http://localhost:8080/
    @CrossOrigin
    @PostMapping("/shorturl")
    public ResponseEntity<UrlDto> add(@RequestBody UrlDto urlDto) {
        var originalUrl = urlDto.getUrl();
        var savedUrl = urlService.createUrl(originalUrl);

        var body = UrlDto.builder()
                .shortUrl(savedUrl)
                .url(originalUrl)
                .build();
        return new ResponseEntity<UrlDto>(body, HttpStatus.OK);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Hello Ping";
    }

}
