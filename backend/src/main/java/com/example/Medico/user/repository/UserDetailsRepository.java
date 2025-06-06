package com.example.Medico.user.repository;

import com.example.Medico.user.model.UserDetails;
import com.example.Medico.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {

    Optional<UserDetails> findByUsers_Id(UUID id);

    UserDetails findByUsers(Users user);
}
