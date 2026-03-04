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

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = userService.findUserById(transaction.payer());
        User receiver = userService.findUserById(transaction.payee());

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = authService.authorize(sender, transaction.value());
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada pelo serviço externo");
        }

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        userService.saveUser(sender);
        userService.saveUser(receiver);
        repository.save(newTransaction);

        try {
            notificationService.sendNotification(sender, "Transferência realizada com sucesso");
            notificationService.sendNotification(receiver, "Você recebeu uma nova transferência");
        } catch (Exception e) {
            System.err.println("Aviso: Falha ao enviar notificação. " + e.getMessage());
        }

        return newTransaction;
    }
}