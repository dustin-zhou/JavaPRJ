package com.example.idempotent.service;

import com.example.idempotent.model.TransferRequest;
import com.example.idempotent.repository.TransferRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private TransferRequestRepository transferRequestRepository;

    @Transactional
    public String processTransfer(String requestId, Long fromAccountId, Long toAccountId, Double amount) {
        // 检查请求是否已经处理过
        Optional<TransferRequest> existingRequest = transferRequestRepository.findByRequestId(requestId);
        if (existingRequest.isPresent()) {
            return "Request already processed.";
        }

        // 创建新的转账请求记录
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(requestId);
        transferRequest.setFromAccountId(fromAccountId);
        transferRequest.setToAccountId(toAccountId);
        transferRequest.setAmount(new BigDecimal(amount));
        transferRequest.setStatus("COMPLETED");

        transferRequestRepository.save(transferRequest);

        // 实际的转账操作应在此处进行（略）

        return "Transfer completed successfully.";
    }
}