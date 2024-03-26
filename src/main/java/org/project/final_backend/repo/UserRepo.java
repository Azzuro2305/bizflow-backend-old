package org.project.final_backend.repo;

import org.project.final_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    Optional<Users> findUsersByMail(String mail);
    Optional<Users> findUsersById(UUID id);
//    @Query("SELECT u.id FROM Users u WHERE u.mail = :mail")
//    Optional<UUID> findIdByMail(@Param("mail") String mail);
}
