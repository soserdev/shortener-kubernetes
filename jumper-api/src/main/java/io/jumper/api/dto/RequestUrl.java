package io.jumper.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUrl {

    private String id;

    // @NotNull
    // @Size(min=7, max=128)
    private String url;

    private String shortUrl;

}
