package org.project.final_backend.repo;

import org.project.final_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    Optional<Users> findUsersByMail(String mail);
    Optional<Users> findUsersById(UUID id);
}
