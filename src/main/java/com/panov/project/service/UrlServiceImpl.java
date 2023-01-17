package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private static final Integer SHORT_URL_LENGTH = 10;
    private final UrlRepository urlRepository;

    @Override
    public List<Url> findAll() {
        return urlRepository.findAll();
    }

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        Url url = new Url();
        url.setUrl(urlDto.getUrlOriginal());
        if (urlDto.getUrlUser() == null || urlDto.getUrlUser().length() == 0) {
            url.setUrlGenerate(generateString());
        } else {
            url.setUrlShort(urlDto.getUrlUser());
            url.setUrlGenerate(urlDto.getUrlUser());
        }
        urlRepository.save(url);
        return url;
    }

    @Override
    public Optional<Url> findUrlByShortLink(String shortLink) {
        return urlRepository.findUrlByUrlGenerate(shortLink);
    }

    private String generateString() {
        Random random = new Random();
        String selection = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(selection.charAt(random.nextInt(selection.length())));
        }
        return sb.toString();
    }
}
