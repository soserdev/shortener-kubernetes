package io.jumper.api.error;

import lombok.Data;

@Data
public class ApiError {

    private String status;
    private String message;
    private String description;

    public ApiError(String status, String message, String error) {
        this.status = status;
        this.message = message;
        this.description = error;
    }

}
