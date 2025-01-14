package com.video.parser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstagramMetrics {
    @JsonProperty("play_count")
    private Long playCount;
    @JsonProperty("like_count")
    private Long likeCount;
    @JsonProperty("comment_count")
    private Long commentCount;
    @JsonProperty("share_count")
    private Long shareCount;

    public Long getPlayCount() {
        return playCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }
}
