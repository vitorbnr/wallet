package com.vitorbnr.wallet.repositories;

import com.vitorbnr.wallet.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacionRepository extends JpaRepository<Transaction, UUID> {
}
