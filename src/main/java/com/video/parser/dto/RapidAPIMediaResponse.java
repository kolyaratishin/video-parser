package com.video.parser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RapidAPIMediaResponse {
    @JsonProperty("data")
    private RapidAPIMediaData data;

    public RapidAPIMediaResponse(RapidAPIMediaData data) {
        this.data = data;
    }

    public RapidAPIMediaData getData() {
        return data;
    }

    public void setData(RapidAPIMediaData data) {
        this.data = data;
    }
}
