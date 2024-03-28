package org.project.final_backend.controller;

import lombok.AllArgsConstructor;
import org.project.final_backend.domain.request.skill.NewSkillRequest;
import org.project.final_backend.domain.request.skill.UpdateSkillRequest;
import org.project.final_backend.domain.response.skill.NewSkillResponse;
import org.project.final_backend.domain.response.skill.UpdateSkillResponse;
import org.project.final_backend.domain.utility.HttpResponse;
import org.project.final_backend.dto.model.SkillInfo;
import org.project.final_backend.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/skill")
public class SkillController {
    final SkillService skillService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewSkillResponse>> addSkill(@PathVariable UUID id, @RequestBody NewSkillRequest request){
        HttpResponse<NewSkillResponse> response =
                new HttpResponse<>(skillService.addSkill(id, request), "Skill added", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<SkillInfo>> getSkill(@PathVariable UUID id){
        HttpResponse<SkillInfo> response =
                new HttpResponse<>(skillService.getSkill(id), "Skill retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateSkillResponse>> updateSkill(@PathVariable UUID id, @RequestBody UpdateSkillRequest request){
        HttpResponse<UpdateSkillResponse> response =
                new HttpResponse<>(skillService.updateSkill(id, request), "Skill updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable UUID id){
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<HttpResponse<List<SkillInfo>>> getAllSkillsByUserId(@PathVariable UUID userId){
        HttpResponse<List<SkillInfo>> response =
                new HttpResponse<>(skillService.getAllSkillsByUserId(userId), "Skills retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
