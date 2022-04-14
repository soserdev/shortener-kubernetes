package io.jumper.api.controller;

import io.jumper.api.dto.UrlDto;
import io.jumper.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;

@RestController()
@Slf4j
public class JumperController {

    private final UrlService urlService;

    @Autowired
    public JumperController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrl:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirect(@PathVariable("shortUrl") String shortUrlPath){
        log.info("JumperController 'GET /shortUrl/" + shortUrlPath);
        var originalUrl = urlService.getUrl(shortUrlPath);
        log.info("JumperController 'GET /shortUrl/'" + shortUrlPath + "' -> originalPath: " + originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    // curl localhost:8080
    @GetMapping("/shorturl/{shortUrl:[a-zA-Z0-9]{6}}")
    public ResponseEntity<UrlDto> get(@PathVariable("shortUrl") String shortUrlPath) {
        var originalUrl = urlService.getUrl(shortUrlPath);
        log.info("JumperController 'GET /shortUrl/'" + originalUrl + "'...");
        var urlDto = UrlDto.builder()
                .shortUrl(shortUrlPath)
                .url(originalUrl)
                .build();
        return new ResponseEntity<>(urlDto, HttpStatus.OK);
    }

    // curl -v -H'Content-Type: application/json' -d'{"url": "http://www.swr3.de"}' http://localhost:8080/
    @CrossOrigin
    @PostMapping("/shorturl")
    public ResponseEntity<UrlDto> add(@RequestBody UrlDto urlDto) {
        var originalUrl = urlDto.getUrl();
        log.info("JumperController 'POST originalUrl: '" + originalUrl + "'...");

        var savedUrl = urlService.createUrl(originalUrl);

        var body = UrlDto.builder()
                .shortUrl(savedUrl)
                .url(originalUrl)
                .build();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/ping")
    public String ping() {
        log.info("Endpoint /ping");
        return "Hello Ping";
    }

}
