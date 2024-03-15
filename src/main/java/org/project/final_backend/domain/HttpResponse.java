package org.project.final_backend.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class HttpResponse<T> {
    private String message;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private LocalDateTime date;
    private T data;

    public HttpResponse(T data, String message, HttpStatus httpStatus){
        this.data = data;
        this.message = message;
        this.httpStatusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.date = LocalDateTime.now();
    }
}
