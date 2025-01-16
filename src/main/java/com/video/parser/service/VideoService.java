package com.video.parser.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.parser.dto.VideoDetailsDto;
import com.video.parser.platform.PlatformClient;
import com.video.parser.platform.instagram.InstagramApiErrorMessageHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<VideoDetailsDto> videos = getAllVideos();
        Arrays.stream(videoUrl.split("\n")).forEach(url -> {
            VideoDetailsDto videoDetails = fetchVideoDetailsByUrl(url);
            if (videoDetails != null){
                videos.add(videoDetails);
            }

        });
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
                VideoDetailsDto updatedVideo = fetchVideoDetailsByUrl(video.getUrl());
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

    public void deleteAllVideos() {
        List<VideoDetailsDto> videos = getAllVideos();
        videos.clear();
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

    private VideoDetailsDto fetchVideoDetailsByUrl(String url) {
        InstagramApiErrorMessageHolder.clear();
        return platformClients.stream()
                .filter(client -> client.supports(url))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported platform"))
                .getVideoDetails(url);
    }
}
