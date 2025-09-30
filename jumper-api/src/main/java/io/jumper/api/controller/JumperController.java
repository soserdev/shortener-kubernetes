package io.jumper.api.controller;

import io.jumper.api.dto.RequestUrl;
import io.jumper.api.dto.ResponseUrl;
import io.jumper.api.exception.SomethingWentWrongException;
import io.jumper.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = {"http://localhost:5175", "http://127.0.0.1"})
@RestController()
@Slf4j
public class JumperController {

    private final UrlService urlService;

    @Autowired
    public JumperController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortUrlPath:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirect(@PathVariable("shortUrlPath") String shortUrlPath){
        log.info("JumperController 'GET /shortUrl/" + shortUrlPath);

        var originalUrl = urlService.getUrl(shortUrlPath);
        if (originalUrl.isEmpty()) {
            log.info("NOT Found: " + shortUrlPath);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("JumperController 'GET /shortUrl/'" + shortUrlPath + "' -> originalPath: " + originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl.get().getOriginalUrl())).build();
    }

    @GetMapping("/shorturl/{shortUrl:[a-zA-Z0-9]{6}}")
    public ResponseEntity<ResponseUrl> getOriginalUrl(@PathVariable("shortUrl") String shortUrlPath) {
        var url = urlService.getUrl(shortUrlPath);
        if (url.isEmpty()) {
            log.info("NOT Found: " + shortUrlPath);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var responseUrl = ResponseUrl.builder()
                .id(url.get().getShortUrl())
                .url(url.get().getOriginalUrl())
                .shortUrl(url.get().getShortUrl())
                .build();
        log.info("JumperController: GET " + shortUrlPath + " -> " + shortUrlPath);
        return new ResponseEntity<>(responseUrl, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/shorturl")
    public ResponseEntity<ResponseUrl> create(@RequestBody RequestUrl urlDto) {
        var originalUrl = urlDto.getUrl();
        var savedUrl = urlService.createUrl(originalUrl);
        if (savedUrl.isEmpty()) {
            throw new SomethingWentWrongException("Url not created");
        }
        log.info("JumperController: POST " + originalUrl + " -> " + savedUrl.get());

        var body = ResponseUrl.builder()
                .id(savedUrl.get())
                .shortUrl(savedUrl.get())
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
