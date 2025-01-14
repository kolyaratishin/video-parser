package com.video.parser.platform.instagram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstagramMediaResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("username")
    private String username;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("like_count")
    private int likeCount;

    @JsonProperty("comments_count")
    private int commentsCount;

    @JsonProperty("video_view_count")
    private int videoViewCount;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getVideoViewCount() {
        return videoViewCount;
    }
}
