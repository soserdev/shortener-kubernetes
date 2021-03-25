package io.jumper.backend.repository;

import io.jumper.backend.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<ShortUrl, String> {

    ShortUrl findByShortUrl(String shortUrlPath);

}
