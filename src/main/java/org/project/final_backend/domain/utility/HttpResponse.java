package org.project.final_backend.domain.utility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class HttpResponse<T> {
    private String message;
    private boolean isSuccess;
    private Meta meta;
    private T data;

    public HttpResponse(T data, String message, HttpStatus httpStatus, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.meta = new Meta(httpStatus.value());
        this.data = data;
    }

    public HttpResponse(String message, HttpStatus httpStatus) {
        this.message = message;
        this.isSuccess = false; // Default to false for error messages
        this.meta = new Meta(httpStatus.value());
    }

    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("code")
        private int code;

        private LocalDateTime date;

        public Meta(int httpStatusCode) {
            this.code = httpStatusCode;
            this.date = LocalDateTime.now();
        }
    }
}