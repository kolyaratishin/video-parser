package com.video.parser.controller;

import com.video.parser.dto.AddVideoRequest;
import com.video.parser.dto.VideoDetailsDto;
import com.video.parser.platform.instagram.InstagramApiErrorMessageHolder;
import com.video.parser.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Обробка додавання нового посилання
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addVideo(@RequestBody AddVideoRequest addVideoRequest) {
        videoService.addVideo(addVideoRequest.getVideoUrl());
        Map<String, Object> response = new HashMap<>();
        response.put("errors", InstagramApiErrorMessageHolder.getErrors());
        return response;
    }

    // Сторінка зі списком всіх відео
    @GetMapping
    public String showVideos(Model model) {
        List<VideoDetailsDto> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        return "video-list";
    }

    // Сторінка зі списком всіх відео
    @GetMapping("/add")
    public String addVideoPage(Model model) {
        model.addAttribute("errors", InstagramApiErrorMessageHolder.getErrors());
        return "add-video";
    }

    @PostMapping("/update")
    public String updateAllVideos(Model model) {
        videoService.updateAllVideos();
        model.addAttribute("message", "All videos updated successfully!");
        return "redirect:/"; // Повертаємося на сторінку зі списком
    }

    @PostMapping("/delete")
    public String deleteVideo(@RequestParam("videoCode") String videoCode) {
        videoService.deleteVideo(videoCode);
        return "redirect:/"; // Перенаправлення на список відео після видалення
    }

    @PostMapping("/delete-all")
    public String deleteAllVideos() {
        videoService.deleteAllVideos();
        return "redirect:/"; // Перенаправлення на список відео після видалення
    }

    @ResponseBody
    @PostMapping("/clear-error-message")
    public ResponseEntity<Void> clearErrorMessages() {
        InstagramApiErrorMessageHolder.clear();
        return ResponseEntity.ok().build();
    }
}
