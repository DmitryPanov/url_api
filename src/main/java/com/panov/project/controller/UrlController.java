package com.panov.project.controller;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
@Slf4j
public class UrlController {
    private final UrlService urlService;
    private final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @GetMapping
    public List<Url> findAllUrls() {
        return urlService.findAll();
    }
    @PostMapping
    public Url createShortLink(@RequestBody UrlDto urlDto) {
        return urlService.createShortDto(urlDto);
    }
}
