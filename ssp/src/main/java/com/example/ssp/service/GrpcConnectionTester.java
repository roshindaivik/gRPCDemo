//package com.example.ssp.service;
//
//import com.grpcjava.bidding.BidRequest;
//import com.grpcjava.bidding.BidResponse;
//import com.grpcjava.bidding.BiddingServiceGrpc;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.StatusRuntimeException;
//
//import java.util.UUID;
//
//public class GrpcConnectionTester {
//    public static void main(String[] args) {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
//                .usePlaintext()
//                .build();
//        BiddingServiceGrpc.BiddingServiceBlockingStub stub = BiddingServiceGrpc.newBlockingStub(channel);
//
//        try {
//            BidRequest request = BidRequest.newBuilder()
//                    .setId(UUID.randomUUID().toString())
//                    .setImpressionId("test-impression")
//                    .setFloorPrice("1.0")
//                    .build();
//            BidResponse response = stub.requestBid(request);
//            System.out.println("Received response: " + response);
//        } catch (StatusRuntimeException e) {
//            System.err.println("RPC failed: " + e.getStatus());
//        } finally {
//            channel.shutdown();
//        }
//    }
//}