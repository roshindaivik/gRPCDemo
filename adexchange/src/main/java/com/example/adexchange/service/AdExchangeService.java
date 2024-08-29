package com.example.adexchange.service;

import com.grpcjava.bidding.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@Slf4j
public class AdExchangeService extends BiddingServiceGrpc.BiddingServiceImplBase {

    @PostConstruct
    public void init() {
        log.info("AdExchangeService initialized and ready to accept gRPC calls on port 9090");
    }

    @Override
    public void requestBid(BidRequest request, StreamObserver<BidResponse> responseObserver) {
        try {
            // Parse the floor price
            double floorPrice = Double.parseDouble(request.getImp().getBidfloor());

            // Calculate bid price (10% above floor price)
            double bidPrice = floorPrice * 1.1;

            // Create a single Bid object
            Bid bid = Bid.newBuilder()
                    .setId(java.util.UUID.randomUUID().toString())
                    .setImpid(request.getImp().getId())
                    .setPrice(bidPrice)
                    .setAdm("Sample ad content for impression: " + request.getImp().getId())
                    .setCrid("creative_" + java.util.UUID.randomUUID().toString())
                    .build();

            // Create a SeatBid object containing the Bid
            SeatBid seatBid = SeatBid.newBuilder()
                    .addBid(bid)
                    .setSeat("bidder_seat_1")
                    .build();

            // Build the full BidResponse
            BidResponse response = BidResponse.newBuilder()
                    .setId(request.getId())
                    .addSeatbid(seatBid)
                    .setBidid(java.util.UUID.randomUUID().toString())
                    .build();

            log.info("Sending bid response: {}", response);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NumberFormatException e) {
            log.error("Invalid floor price: {}", request.getImp().getBidfloor(), e);
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Invalid floor price")
                    .asRuntimeException());
        }
    }
}