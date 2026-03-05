package com.vitorbnr.wallet.service;

import com.vitorbnr.wallet.domain.transaction.Transaction;
import com.vitorbnr.wallet.domain.user.User;
import com.vitorbnr.wallet.domain.user.UserType;
import com.vitorbnr.wallet.dtos.TransactionDTO;
import com.vitorbnr.wallet.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("Deve realizar a transferência com sucesso (Caminho Feliz)")
    void createTransactionSuccess() throws Exception {
        User sender = new User("Vitor", "Bernardo", "123", "vitor@teste.com", "senha", new BigDecimal("100.00"), UserType.COMMON);
        sender.setId(UUID.randomUUID());

        User receiver = new User("Loja", "Dev", "456", "loja@teste.com", "senha", new BigDecimal("0.00"), UserType.MERCHANT);
        receiver.setId(UUID.randomUUID());

        TransactionDTO requestDTO = new TransactionDTO(new BigDecimal("50.00"), sender.getId(), receiver.getId());

        Mockito.when(userService.findUserById(sender.getId())).thenReturn(sender);
        Mockito.when(userService.findUserById(receiver.getId())).thenReturn(receiver);
        Mockito.when(authService.authorize(any(User.class), any(BigDecimal.class))).thenReturn(true);

        Transaction novaTransacao = transactionService.createTransaction(requestDTO);

        Assertions.assertEquals(new BigDecimal("50.00"), sender.getBalance());
        Assertions.assertEquals(new BigDecimal("50.00"), receiver.getBalance());

        Mockito.verify(userService, times(1)).saveUser(sender);
        Mockito.verify(userService, times(1)).saveUser(receiver);

        Mockito.verify(notificationService, times(1)).sendNotification(Mockito.eq(sender), Mockito.anyString());

        Mockito.verify(notificationService, times(1)).sendNotification(Mockito.eq(receiver), Mockito.anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o serviço externo não autorizar a transação")
    void createTransactionUnauthorized() throws Exception {
        User sender = new User("Vitor", "Bernardo", "123", "vitor@teste.com", "senha", new BigDecimal("100.00"), UserType.COMMON);
        sender.setId(UUID.randomUUID());

        User receiver = new User("Loja", "Dev", "456", "loja@teste.com", "senha", new BigDecimal("0.00"), UserType.MERCHANT);
        receiver.setId(UUID.randomUUID());

        TransactionDTO requestDTO = new TransactionDTO(new BigDecimal("50.00"), sender.getId(), receiver.getId());

        Mockito.when(userService.findUserById(sender.getId())).thenReturn(sender);
        Mockito.when(userService.findUserById(receiver.getId())).thenReturn(receiver);
        Mockito.when(authService.authorize(any(User.class), any(BigDecimal.class))).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            transactionService.createTransaction(requestDTO);
        });

        Assertions.assertEquals("Transação não autorizada pelo serviço externo", thrown.getMessage());

        Mockito.verify(userService, times(0)).saveUser(any());
        Mockito.verify(repository, times(0)).save(any());
    }
}