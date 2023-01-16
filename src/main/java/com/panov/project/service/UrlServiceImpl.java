package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;


    @Override
    public List<Url> findAll() {
        return urlRepository.findAll();
    }

    @Override
    public Url createShortDto(UrlDto urlDto) {
        Url url = new Url();
        url.setUrl(urlDto.getUrlOriginal());
        url.setUrlShort(urlDto.getUrlUser());
        url.setUrlGenerate(urlDto.getUrlUser());
        urlRepository.save(url);
        return url;
    }
}
