package org.project.final_backend.domain.request.workexp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewWorkExpRequest {
    private String companyName;
    private String position;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
}
