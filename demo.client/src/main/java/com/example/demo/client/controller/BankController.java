package com.example.demo.client.controller;


import com.example.demo.client.dto.ChatResponse;
import com.example.demo.client.service.BankService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/request")
    public ResponseEntity<ChatResponse> getRequest(@RequestBody String message) throws JsonProcessingException {

        return ResponseEntity.ok(bankService.analyseUserRequest(message));
    }
}