package com.panov.project.controller;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
@Slf4j
public class UrlController {
    private final UrlService urlService;
    private final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @GetMapping
    public ResponseEntity<?> findAllUrls() {
        return ResponseEntity.ok(urlService.findAllUrl());
    }

    @PostMapping
    public ResponseEntity<?> createShortLink(@RequestBody UrlDto urlDto) {
        return new ResponseEntity<>(urlService.generateShortLink(urlDto), HttpStatus.CREATED);
    }

    @GetMapping("/{link}")
    public ResponseEntity<?> redirectToShortLink(@PathVariable String link) {
        return ResponseEntity.ok(urlService.findUrlByShortLink(link));
    }


}
