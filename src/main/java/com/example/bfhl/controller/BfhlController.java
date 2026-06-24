package com.example.bfhl.controller;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import com.example.bfhl.service.BfhlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> handleBfhl(@RequestBody BfhlRequest request) {
        try {
            BfhlResponse response = bfhlService.process(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            BfhlResponse errorResponse = new BfhlResponse(
                    false, null, null, null, null, null, null, null, null, null
            );
            return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        }
    }
}