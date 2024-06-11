package org.project.final_backend.domain.utility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class CustomPaginationResponse<T> {
    private int code;
    private String message;
    private boolean isSuccess;
    private Meta meta;
    private List<T> data;

    public CustomPaginationResponse(List<T> data, Meta meta, HttpStatus httpStatus, String message, boolean isSuccess) {
        this.meta = meta;
        this.code = httpStatus.value();
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    @Getter
    @Setter
    public static class Meta {
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private int totalPages;

        public Meta(Page<?> page, int originalPageNumber) {
            this.pageNumber = originalPageNumber; // Use the original page number provided in the request
            this.pageSize = page.getSize();
            this.totalElements = page.getTotalElements();
            this.totalPages = page.getTotalPages();
        }


    }
}
