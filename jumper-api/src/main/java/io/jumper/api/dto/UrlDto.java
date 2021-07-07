package io.jumper.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlDto {

    // @NotNull
    // @Size(min=7, max=128)
    private String url;

    // https://stackoverflow.com/questions/62029674/mongodb-unique-index-not-created-in-spring-boot
    // https://www.baeldung.com/spring-data-mongodb-index-annotations-converter
    // https://stackoverflow.com/questions/49385130/spring-data-unique-field-in-mongodb-document/58339272
    // https://www.baeldung.com/spring-data-mongodb-tutorial
    // https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference
    @Indexed(unique = true)
    private String shortUrl;

}
