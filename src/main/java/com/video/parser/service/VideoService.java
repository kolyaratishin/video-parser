package com.video.parser.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.parser.dto.Platform;
import com.video.parser.dto.VideoDetailsDto;
import com.video.parser.platform.PlatformClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private final List<PlatformClient> platformClients;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String filePath = "videos.json";

    public VideoService(List<PlatformClient> platformClients) {
        this.platformClients = platformClients;
    }

    public void addVideo(String videoUrl) {
        VideoDetailsDto videoDetails = fetchVideoDetailsByUrl(videoUrl);
        List<VideoDetailsDto> videos = getAllVideos();
        videos.add(videoDetails);
        saveVideosToFile(videos);
    }

    // Отримати всі відео
    public List<VideoDetailsDto> getAllVideos() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read video data from file", e);
        }
    }

    // Оновити всі відео у файлі
    public void updateAllVideos() {
        List<VideoDetailsDto> videos = getAllVideos();
        List<VideoDetailsDto> updatedVideos = new ArrayList<>();

        for (VideoDetailsDto video : videos) {
            try {
                // Оновлюємо дані для кожного відео
                VideoDetailsDto updatedVideo = fetchVideoDetailsByCode(video.getCode(), video.getPlatform());
                updatedVideos.add(updatedVideo);
            } catch (Exception e) {
                System.err.println("Failed to update video: " + video.getCode() + ". Error: " + e.getMessage());
                updatedVideos.add(video); // Якщо оновлення не вдалося, залишаємо старі дані
            }
        }

        saveVideosToFile(updatedVideos);
    }

    public void deleteVideo(String videoCode) {
        List<VideoDetailsDto> videos = getAllVideos();
        videos.removeIf(video -> video.getCode().equals(videoCode)); // Видаляємо відео за кодом
        saveVideosToFile(videos);
    }

    // Зберегти всі відео у файл
    private void saveVideosToFile(List<VideoDetailsDto> videos) {
        try {
            objectMapper.writeValue(new File(filePath), videos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save video data to file", e);
        }
    }

    private VideoDetailsDto fetchVideoDetailsByCode(String code, Platform platform) {
        return platformClients.stream()
                .filter(client -> client.supports(platform))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported platform"))
                .getVideoDetails(code);
    }

    private VideoDetailsDto fetchVideoDetailsByUrl(String url) {
        String videoId = fetchVideoIdFromUrl(url);
        return platformClients.stream()
                .filter(client -> client.supports(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported platform"))
                .getVideoDetails(videoId);
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
