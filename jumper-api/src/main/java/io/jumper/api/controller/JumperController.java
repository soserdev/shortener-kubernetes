package io.jumper.api.controller;

import io.jumper.api.dto.UrlDto;
import io.jumper.api.exception.SomethingWentWrongException;
import io.jumper.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
        if (originalUrl.isEmpty()) {
            log.info("NOT Found: " + shortUrlPath);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("JumperController 'GET /shortUrl/'" + shortUrlPath + "' -> originalPath: " + originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl.get())).build();
    }

    @GetMapping("/shorturl/{shortUrl:[a-zA-Z0-9]{6}}")
    public ResponseEntity<UrlDto> getOriginalUrl(@PathVariable("shortUrl") String shortUrlPath) {
        var originalUrl = urlService.getUrl(shortUrlPath);
        if (originalUrl.isEmpty()) {
            log.info("NOT Found: " + shortUrlPath);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var urlDto = UrlDto.builder()
                .url(originalUrl.get())
                .shortUrl(shortUrlPath)
                .build();
        log.info("JumperController: GET " + shortUrlPath + " -> " + originalUrl.get());
        return new ResponseEntity<>(urlDto, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/shorturl")
    public ResponseEntity<UrlDto> create(@RequestBody UrlDto urlDto) {
        var originalUrl = urlDto.getUrl();
        var savedUrl = urlService.createUrl(originalUrl);
        if (savedUrl.isEmpty()) {
            throw new SomethingWentWrongException("Url not created");
        }
        log.info("JumperController: POST " + originalUrl + " -> " + savedUrl.get());

        var body = UrlDto.builder()
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
