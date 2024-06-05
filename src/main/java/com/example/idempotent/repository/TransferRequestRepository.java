package com.example.idempotent.repository;

import com.example.idempotent.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
    Optional<TransferRequest> findByRequestId(String requestId);
}