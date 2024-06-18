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
@RequestMapping("/api/skills")
public class SkillController {
    final SkillService skillService;

    @PostMapping("/{id}")
    public ResponseEntity<HttpResponse<NewSkillResponse>> addSkill(@PathVariable UUID id, @RequestBody NewSkillRequest request){
        NewSkillResponse skillResponse = skillService.addSkill(id, request);
        HttpResponse<NewSkillResponse> response =
                new HttpResponse<>(skillResponse,skillResponse != null , "Skill added", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<HttpResponse<SkillInfo>> getSkill(@PathVariable UUID id){
        SkillInfo skillInfo = skillService.getSkill(id);
        HttpResponse<SkillInfo> response =
                new HttpResponse<>(skillInfo, skillInfo != null, "Skill retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UpdateSkillResponse>> updateSkill(@PathVariable UUID id, @RequestBody UpdateSkillRequest request){
        UpdateSkillResponse skillResponse = skillService.updateSkill(id, request);
        HttpResponse<UpdateSkillResponse> response =
                new HttpResponse<>(skillResponse, skillResponse != null, "Skill updated", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse<String>> deleteSkill(@PathVariable UUID id){
        skillService.deleteSkill(id);
        HttpResponse<String> response = new HttpResponse<>("Skill deleted", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<HttpResponse<List<SkillInfo>>> getAllSkillsByUserId(@PathVariable UUID userId){
        List<SkillInfo> skills = skillService.getAllSkillsByUserId(userId);
        HttpResponse<List<SkillInfo>> response =
                new HttpResponse<>(skills, skills != null, "Skills retrieved", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
