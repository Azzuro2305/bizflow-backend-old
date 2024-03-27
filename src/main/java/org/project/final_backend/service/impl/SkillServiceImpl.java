package org.project.final_backend.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.domain.request.skill.NewSkillRequest;
import org.project.final_backend.domain.request.skill.UpdateSkillRequest;
import org.project.final_backend.domain.response.skill.NewSkillResponse;
import org.project.final_backend.domain.response.skill.UpdateSkillResponse;
import org.project.final_backend.dto.model.SkillInfo;
import org.project.final_backend.entity.Skill;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.SkillRepo;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.SkillService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepo skillRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public NewSkillResponse addSkill(UUID id, NewSkillRequest request) {
        Users user = userRepo.findUsersById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Skill skill = Skill.builder()
                .skillName(request.getSkillName())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
        Skill savedSkill = skillRepo.save(skill);
        return modelMapper.map(savedSkill, NewSkillResponse.class);
    }

    @Override
    public SkillInfo getSkill(UUID id) {
        final Skill skill = skillRepo.findSkillById(id)
                .orElseThrow(() -> new UserNotFoundException("Skill not found"));
        return modelMapper.map(skill, SkillInfo.class);
    }

    @Override
    public UpdateSkillResponse updateSkill(UUID id, UpdateSkillRequest request) {
        Skill skill = skillRepo.findSkillById(id)
                .orElseThrow(() -> new UserNotFoundException("Skill not found"));
        skill.setSkillName(request.getSkillName() != null ? request.getSkillName() : skill.getSkillName());
        skill.setUpdatedDate(LocalDateTime.now());
        Skill updatedSkill = skillRepo.save(skill);
        return modelMapper.map(updatedSkill, UpdateSkillResponse.class);
    }

    @Override
    public void deleteSkill(UUID id) {
        Skill skill = skillRepo.findSkillById(id)
                .orElseThrow(() -> new UserNotFoundException("Skill not found"));
        skillRepo.delete(skill);
    }

    @Override
    public List<SkillInfo> getAllSkillsByUserId(UUID userId) {
        List<Skill> skills = skillRepo.findAllByUserId(userId);
        return skills.stream()
                .map(skill -> modelMapper.map(skill, SkillInfo.class))
                .collect(Collectors.toList());
    }
}