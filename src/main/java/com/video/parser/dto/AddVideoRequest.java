package com.video.parser.dto;

public class AddVideoRequest {
    private String videoUrl;

    public AddVideoRequest() {
    }

    public AddVideoRequest(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
