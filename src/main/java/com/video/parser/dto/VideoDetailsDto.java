package com.video.parser.dto;

public class VideoDetailsDto {
    private String code;
    private Long views;
    private Long likes;
    private Long comments;
    private Platform platform;

    public VideoDetailsDto() {
    }

    public VideoDetailsDto(String code, Long views, Long likes, Long comments, Platform platform) {
        this.code = code;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.platform = platform;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}

