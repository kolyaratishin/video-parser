package com.video.parser.controller;

import com.video.parser.dto.VideoDetailsDto;
import com.video.parser.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Обробка додавання нового посилання
    @PostMapping("/add")
    public String addVideo(@RequestParam("videoUrl") String videoUrl, Model model) {
        videoService.addVideo(videoUrl);
        model.addAttribute("message", "Video added successfully!");
        return "redirect:/"; // Перенаправляємо на сторінку зі списком
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

}
