package io.jumper.api.repository;

import io.jumper.api.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<ShortUrl, String> {

    ShortUrl findByShortUrl(String shortUrlPath);

}
