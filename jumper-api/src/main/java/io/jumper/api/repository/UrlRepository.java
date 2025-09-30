package io.jumper.api.repository;

import io.jumper.api.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {

    Url findByShortUrl(String shortUrlPath);

}
