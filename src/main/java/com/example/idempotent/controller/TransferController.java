package com.example.idempotent.controller;

import com.example.idempotent.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public String transfer(@RequestParam String requestId,
                           @RequestParam Long fromAccountId,
                           @RequestParam Long toAccountId,
                           @RequestParam Double amount) {
        return transferService.processTransfer(requestId, fromAccountId, toAccountId, amount);
    }
}