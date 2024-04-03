package org.project.final_backend.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Setter
public class Subscribe {
    private UUID userId;
    private String subscriptionType;
}
