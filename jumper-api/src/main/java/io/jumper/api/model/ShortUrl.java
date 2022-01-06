package io.jumper.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "urls")
public class ShortUrl {

    @Id
    private String id;

    @Indexed(unique = true)
    private String shortUrl;

    private String originalUrl;

}
