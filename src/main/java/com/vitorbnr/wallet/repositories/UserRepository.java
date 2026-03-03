package com.vitorbnr.wallet.repositories;

import com.vitorbnr.wallet.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByDocument(String document);

    Optional<User> findUserByEmail(String email);
}