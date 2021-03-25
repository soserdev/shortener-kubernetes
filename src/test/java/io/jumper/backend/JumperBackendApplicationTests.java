package io.jumper.backend;

import io.jumper.backend.model.ShortUrl;
import io.jumper.backend.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JumperBackendApplicationTests {

	@Autowired
	private UrlRepository urlRepository;

	@Test
	void contextLoads() {
		var shortUrl = "abc123";
		var url = new ShortUrl();
		url.setShortUrl(shortUrl);
		url.setOriginalUrl("https//www.swr3.de");
		var savedUrl = urlRepository.save(url);
		System.out.println(savedUrl.getId());
		System.out.println(savedUrl.getShortUrl());
		System.out.println(savedUrl.getOriginalUrl());

		var foundUrl = urlRepository.findByShortUrl(shortUrl);
		System.out.println(foundUrl.getId());
		System.out.println(foundUrl.getShortUrl());
		System.out.println(foundUrl.getOriginalUrl());
	}

}
