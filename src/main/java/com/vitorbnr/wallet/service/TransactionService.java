package com.vitorbnr.wallet.service;

import com.vitorbnr.wallet.domain.transaction.Transaction;
import com.vitorbnr.wallet.domain.user.User;
import com.vitorbnr.wallet.dtos.TransactionDTO;
import com.vitorbnr.wallet.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = userService.findUserById(transaction.payer());
        User receiver = userService.findUserById(transaction.payee());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        userService.saveUser(sender);
        userService.saveUser(receiver);

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        return repository.save(newTransaction);

        // notificationService.sendNotification(sender, "Dinheiro enviado");
        // notificationService.sendNotification(receiver, "Dinheiro recebido");
    }

    public boolean authorizeTransaction(User sender, java.math.BigDecimal value) {
        return true;
    }
}