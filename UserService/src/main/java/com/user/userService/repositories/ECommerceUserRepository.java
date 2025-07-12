package com.user.userService.repositories;

import com.user.userService.models.ECommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ECommerceUserRepository extends JpaRepository<ECommerceUser, Long> {
    Optional<ECommerceUser> findByUsername(String username);
}