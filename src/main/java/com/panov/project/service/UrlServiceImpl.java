package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.exception_handler.ResourceNotFoundException;
import com.panov.project.exception_handler.UrlException;
import com.panov.project.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
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
        return Optional.of(urlRepository.findAll()).orElse(Collections.emptyList());
    }

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        checkOriginalUrl(urlDto);
        Url url = checkOriginalUrlInDB(urlDto);
        if (url != null) {
            return url;
        } else {
            url = new Url();
        }
        url.setUrl(urlDto.getUrlOriginal());
        if (urlDto.getUrlUser() == null || urlDto.getUrlUser().length() == 0) {
            url.setUrlGenerate(generateString(urlDto));
        } else {
            url.setUrlShort(urlDto.getUrlUser());
            url.setUrlGenerate(urlDto.getUrlUser());
        }
        urlRepository.save(url);
        return url;
    }

    private Url checkOriginalUrlInDB(UrlDto urlDto) {
        return urlRepository.findUrlByUrl(urlDto.getUrlOriginal());
    }

    private void checkOriginalUrl(UrlDto urlDto) {
        try {
            new URL(urlDto.getUrlOriginal()).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
            throw new UrlException("Exception in URL link " + e.getMessage());
        }
    }

    @Override
    public Url findUrlByShortLink(String shortLink) {
        return urlRepository.findUrlByUrlGenerate(shortLink).orElseThrow(() -> new ResourceNotFoundException("Not found short Url: " + shortLink));
    }

    private String generateString(UrlDto urlDto) {
        Random random = new Random();
        String selection = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(selection.charAt(random.nextInt(selection.length())));
        }
        return sb.toString();
    }
}
