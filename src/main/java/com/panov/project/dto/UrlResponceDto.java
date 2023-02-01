package com.panov.project.dto;

import com.panov.project.entity.Url;
import lombok.Builder;
import lombok.Data;

@Data
public class UrlResponceDto {
    private Long id;
    private String url;
    private String urlShort;
    private String urlGenerate;

    public UrlResponceDto(Url url) {
        this.id = url.getId();
        this.url = url.getUrl();
        this.urlShort = url.getUrlShort();
        this.urlGenerate = url.getUrlGenerate();
    }
}
