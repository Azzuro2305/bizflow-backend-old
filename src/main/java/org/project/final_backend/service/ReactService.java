package org.project.final_backend.service;

import org.project.final_backend.domain.request.react.NewReactRequest;
import org.project.final_backend.domain.response.react.NewReactResponse;
import org.project.final_backend.entity.React;

import java.util.UUID;


public interface ReactService {

    React findReactById(UUID id);
    NewReactResponse createReact(NewReactRequest request);
}
