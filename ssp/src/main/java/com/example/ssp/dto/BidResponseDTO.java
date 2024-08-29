package com.example.ssp.dto;

import java.util.List;
import java.util.stream.Collectors;

public record BidResponseDTO(
        String id,
        List<SeatBid> seatbid,
        String bidid,
        String cur
) {
    public record SeatBid(
            List<Bid> bid,
            String seat
    ) {}

    public record Bid(
            String id,
            String impid,
            Double price,
            String adm,
            String crid,
            String adomain
    ) {}

    public static BidResponseDTO fromGrpcResponse(com.grpcjava.bidding.BidResponse grpcResponse) {
        return new BidResponseDTO(
                grpcResponse.getId(),
                grpcResponse.getSeatbidList().stream()
                        .map(seatBid -> new SeatBid(
                                seatBid.getBidList().stream()
                                        .map(bid -> new Bid(
                                                bid.getId(),
                                                bid.getImpid(),
                                                bid.getPrice(),
                                                bid.getAdm(),
                                                bid.getCrid(),
                                                bid.getAdomain()
                                        ))
                                        .collect(Collectors.toList()),
                                seatBid.getSeat()
                        ))
                        .collect(Collectors.toList()),
                grpcResponse.getBidid(),
                grpcResponse.getCur()
        );
    }
}
