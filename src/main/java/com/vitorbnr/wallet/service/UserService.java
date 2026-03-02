package com.vitorbnr.wallet.service;

import com.vitorbnr.wallet.domain.user.User;
import com.vitorbnr.wallet.domain.user.UserType;
import com.vitorbnr.wallet.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}