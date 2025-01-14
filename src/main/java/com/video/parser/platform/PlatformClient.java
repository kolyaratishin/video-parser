package com.video.parser.platform;

import com.video.parser.dto.Platform;
import com.video.parser.dto.VideoDetailsDto;

public interface PlatformClient {
    boolean supports(String url);
    boolean supports(Platform platform);
    VideoDetailsDto getVideoDetails(String url);
}
