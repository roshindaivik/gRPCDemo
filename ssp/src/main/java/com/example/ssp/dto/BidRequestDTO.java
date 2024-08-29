package com.example.ssp.dto;

import java.util.List;

public record BidRequestDTO(
        String id,
        Imp imp,
        Site site,
        Device device,
        User user
) {
    public record Imp(
            String id,
            Banner banner,
            Video video,
            String bidfloor,
            String bidfloorcur
    ) {}

    public record Banner(
            List<Integer> w,
            List<Integer> h,
            Integer pos
    ) {}

    public record Video(
            List<String> mimes,
            Integer minduration,
            Integer maxduration
    ) {}

    public record Site(
            String id,
            String domain
    ) {}

    public record Device(
            String ua,
            String ip,
            Geo geo
    ) {}

    public record Geo(
            Float lat,
            Float lon,
            String country
    ) {}

    public record User(
            String id,
            String buyeruid
    ) {}
}
