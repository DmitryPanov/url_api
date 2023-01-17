package com.panov.project.controller;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return urlService.generateShortLink(urlDto);
    }

    @GetMapping("/{link}")
    public ResponseEntity<?> redirectToShortLink(@PathVariable String link) {
        Url url = urlService.findUrlByShortLink(link);
        logger.warn(String.valueOf(url));
        return ResponseEntity.ok(url);
    }


}
