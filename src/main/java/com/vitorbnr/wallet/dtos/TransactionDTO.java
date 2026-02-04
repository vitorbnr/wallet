package com.vitorbnr.wallet.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(
        BigDecimal value,
        UUID payer,
        UUID payee
) {
}