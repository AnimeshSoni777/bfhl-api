package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;

public interface BfhlService {
    BfhlResponse process(BfhlRequest request);
}
