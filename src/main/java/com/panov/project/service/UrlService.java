package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;

import java.util.List;

public interface UrlService {
    List<Url> findAllUrl();
    Url generateShortLink(UrlDto urlDto);
    Url findUrlByShortLink(String shortLink);
}
