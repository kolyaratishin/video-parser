package com.video.parser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RapidAPIMediaData {
    @JsonProperty("caption")
    private InstagramCaption caption;

    @JsonProperty("metrics")
    private InstagramMetrics metrics;

    @JsonProperty("code")
    private String code;

    public InstagramMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(InstagramMetrics metrics) {
        this.metrics = metrics;
    }

    public InstagramCaption getCaption() {
        return caption;
    }

    public void setCaption(InstagramCaption caption) {
        this.caption = caption;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

