package com.video.parser.platform.instagram;

import com.video.parser.dto.Platform;
import com.video.parser.dto.RapidAPIMediaResponse;
import com.video.parser.dto.VideoDetailsDto;
import com.video.parser.platform.PlatformClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Component
public class RapidAPIInstagramClient implements PlatformClient {

    private final WebClient webClient;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    public RapidAPIInstagramClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://instagram-scraper-api2.p.rapidapi.com").build();
    }

    @Override
    public boolean supports(String url) {
        return url.contains("instagram.com");
    }

    @Override
    public boolean supports(Platform platform) {
        return Arrays.stream(Platform.values()).anyMatch(p -> p == platform);
    }

    @Override
    public VideoDetailsDto getVideoDetails(String url) {
        String videoId = fetchVideoIdFromUrl(url);
        String apiUrl = "v1/post_info?code_or_id_or_url=" + videoId + "&include_insights=true";

        return webClient.get()
                .uri(apiUrl)
                .header("x-rapidapi-host", "instagram-scraper-api2.p.rapidapi.com")
                .header("x-rapidapi-key", rapidApiKey)
                .retrieve()
                .bodyToMono(RapidAPIMediaResponse.class)
                .map(response -> new VideoDetailsDto(
                        response.getData().getCode(),
                        response.getData().getMetrics().getPlayCount(),
                        response.getData().getMetrics().getLikeCount(),
                        response.getData().getMetrics().getCommentCount(),
                        Platform.INSTAGRAM,
                        url
                ))
                .block();
    }

    private String fetchVideoIdFromUrl(String videoUrl) {
        // Розбиваємо URL на частини
        String[] parts = videoUrl.split("/");

        // Знаходимо ID, який розташований після "instagram.com/p/"
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("p") && i + 1 < parts.length) {
                return parts[i + 1]; // Наступна частина - це ID поста
            }
        }
        throw new IllegalArgumentException("Unable to extract video ID from the URL");
    }
}