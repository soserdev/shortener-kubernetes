package io.jumper.api;

import io.jumper.api.model.ShortUrl;
import io.jumper.api.repository.UrlRepository;
import io.jumper.api.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JumperApiApplicationTests {

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private UrlService urlService;

	@Test
	void contextLoads() {
		var url = new ShortUrl();
		var shortUrl = "abc123";
		url.setOriginalUrl("https//www.swr3.de");
		url.setShortUrl(shortUrl);
		var savedUrl = urlRepository.save(url);
		System.out.println(savedUrl.getId());
		System.out.println(savedUrl.getShortUrl());
		System.out.println(savedUrl.getOriginalUrl());

		var foundUrl = urlRepository.findByShortUrl(savedUrl.getShortUrl());
		System.out.println(foundUrl.getId());
		System.out.println(foundUrl.getShortUrl());
		System.out.println(foundUrl.getOriginalUrl());

		urlRepository.deleteById(savedUrl.getId());
	}

}
