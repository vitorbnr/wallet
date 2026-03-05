package com.vitorbnr.wallet.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
        @NotNull(message = "O valor da transferência não pode ser nulo")
        @Positive(message = "O valor da transferência deve ser maior que zero")
        BigDecimal value,

        @NotNull(message = "O ID do pagador é obrigatório")
        UUID payer,

        @NotNull(message = "O ID do recebedor é obrigatório")
        UUID payee
) {
}