package com.vitorbnr.wallet.dtos;

import com.vitorbnr.wallet.domain.user.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UserDTO(
        @NotBlank(message = "O nome é obrigatório")
        String firstName,

        @NotBlank(message = "O sobrenome é obrigatório")
        String lastName,

        @NotBlank(message = "O documento (CPF/CNPJ) é obrigatório")
        String document,

        @NotNull(message = "O saldo inicial não pode ser nulo")
        @PositiveOrZero(message = "O saldo inicial não pode ser negativo")
        BigDecimal balance,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password,

        @NotNull(message = "O tipo de usuário é obrigatório")
        UserType userType
) {
}