package com.example.ssp.controller;

import com.example.ssp.dto.BidRequestDTO;
import com.example.ssp.dto.BidResponseDTO;
import com.example.ssp.service.SspService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grpcjava.bidding.BidRequest;
import com.grpcjava.bidding.BidResponse;
import com.grpcjava.bidding.BiddingServiceGrpc;

@RestController
@Slf4j
@RequestMapping("/api/ssp")
@AllArgsConstructor
public class SspController {

    private final SspService sspService;

    @PostMapping("/bidrequest")
    public BidResponseDTO requestBid(@RequestBody BidRequestDTO bidRequestDTO){
        log.info(String.valueOf(bidRequestDTO));
        BidResponseDTO response = sspService.requestBid(bidRequestDTO);
        return response;
    }
}
