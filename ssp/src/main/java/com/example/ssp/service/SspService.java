package com.example.ssp.service;

import com.example.ssp.dto.BidRequestDTO;
import com.example.ssp.dto.BidResponseDTO;
import com.grpcjava.bidding.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;

@GrpcService
@Slf4j
public class SspService {

    @GrpcClient("adexchange")
    private BiddingServiceGrpc.BiddingServiceBlockingStub adExchangeStub;


    public SspService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.adExchangeStub = BiddingServiceGrpc.newBlockingStub(channel);
        //single request single response
    }

    public BidResponseDTO requestBid(BidRequestDTO bidRequestDTO) {
        log.info("log1");

        BidRequest.Builder requestBuilder = BidRequest.newBuilder()
                .setId(bidRequestDTO.id());

        BidRequestDTO.Imp impDTO = bidRequestDTO.imp();
        if (impDTO != null) {
            Imp.Builder impBuilder = Imp.newBuilder()
                    .setId(impDTO.id())
                    .setBidfloor(impDTO.bidfloor() != null ? impDTO.bidfloor().toString() : "")
                    .setBidfloorcur(impDTO.bidfloorcur());

            if (impDTO.banner() != null) {
                Banner.Builder bannerBuilder = Banner.newBuilder();
                if (impDTO.banner().w() != null) {
                    bannerBuilder.addAllW(impDTO.banner().w());
                }
                if (impDTO.banner().h() != null) {
                    bannerBuilder.addAllH(impDTO.banner().h());
                }
                if (impDTO.banner().pos() != null) {
                    bannerBuilder.setPos(impDTO.banner().pos());
                }
                impBuilder.setBanner(bannerBuilder);
            }

            if (impDTO.video() != null) {
                Video.Builder videoBuilder = Video.newBuilder()
                        .addAllMimes(impDTO.video().mimes())
                        .setMinduration(impDTO.video().minduration())
                        .setMaxduration(impDTO.video().maxduration());
                impBuilder.setVideo(videoBuilder);
            }

            requestBuilder.setImp(impBuilder);
        }

        if (bidRequestDTO.site() != null) {
            requestBuilder.setSite(Site.newBuilder()
                    .setId(bidRequestDTO.site().id())
                    .setDomain(bidRequestDTO.site().domain()));
        }

        if (bidRequestDTO.device() != null) {
            Device.Builder deviceBuilder = Device.newBuilder()
                    .setUa(bidRequestDTO.device().ua())
                    .setIp(bidRequestDTO.device().ip());

            if (bidRequestDTO.device().geo() != null) {
                deviceBuilder.setGeo(Geo.newBuilder()
                        .setLat(bidRequestDTO.device().geo().lat())
                        .setLon(bidRequestDTO.device().geo().lon())
                        .setCountry(bidRequestDTO.device().geo().country()));
            }

            requestBuilder.setDevice(deviceBuilder);
        }

        if (bidRequestDTO.user() != null) {
            requestBuilder.setUser(User.newBuilder()
                    .setId(bidRequestDTO.user().id())
                    .setBuyeruid(bidRequestDTO.user().buyeruid()));
        }

        BidRequest request = requestBuilder.build();

        log.info("log2");

        // Assume adExchangeStub is an instance of the gRPC stub for sending the request.
        BidResponse grpcResponse = adExchangeStub.requestBid(request);

        return BidResponseDTO.fromGrpcResponse(grpcResponse);
    }

}
