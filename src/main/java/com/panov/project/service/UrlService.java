package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<Url> findAll();
    Url generateShortLink(UrlDto urlDto);
    Url findUrlByShortLink(String shortLink);
}
