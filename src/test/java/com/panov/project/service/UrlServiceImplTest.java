package com.panov.project.service;

import com.panov.project.dto.UrlDto;
import com.panov.project.entity.Url;
import com.panov.project.exception_handler.UrlException;
import com.panov.project.repository.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlRepository urlRepository;
    private UrlServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UrlServiceImpl(urlRepository);
    }

    @Test
    void findAllUrl() {
        underTest.findAllUrl();
        verify(urlRepository).findAll();
    }

    @Test
    void generateShortLink() {
        UrlDto urlDto = new UrlDto("https://www.google.com/", "test");
        Url url = underTest.generateShortLink(urlDto);

        ArgumentCaptor<Url> urlArgumentCaptor = ArgumentCaptor.forClass(Url.class);

        verify(urlRepository).save(urlArgumentCaptor.capture());

        Url capturedUrl = urlArgumentCaptor.getValue();

        assertThat(capturedUrl).isEqualTo(url);
    }

    @Test
    void willThrowExceptionWhenUrlBad() {
        String badLink = "hps://www.google.com/";
        UrlDto urlDto = new UrlDto(badLink, "test");

        assertThatThrownBy(() -> underTest.generateShortLink(urlDto))
                .isInstanceOf(UrlException.class)
                .hasMessage("Exception in URL link unknown protocol: hps");

        verify(urlRepository, never()).save(any());
    }

    @Test
    void willReturnUrlFromDBIfOriginalLinkExist() {
        Url url = Url.builder().id(1L).url("https://www.google.com/").urlShort("test").urlGenerate("test").build();

        Mockito
                .when(urlRepository.findUrlByUrl("https://www.google.com/"))
                .thenReturn(url);

        Url shortLink = underTest.generateShortLink(new UrlDto("https://www.google.com/", ""));

        Assertions.assertEquals(url, shortLink);
    }

    @Test
    void generateStringWithoutUserShortUrl() {
        Url shortLink = underTest.generateShortLink(new UrlDto("https://www.google.com/", ""));

        Assertions.assertEquals(shortLink.getUrlGenerate().length(), 10);
        Assertions.assertNull(shortLink.getUrlShort());
    }

    @Test
    void findUrlByShortLink() {

        Url url = Url.builder().id(1L).url("https://www.google.com/").urlShort("test").urlGenerate("test").build();

        Mockito
                .when(urlRepository.findUrlByUrlGenerate("test"))
                .thenReturn(Optional.ofNullable(url));

        Url test = underTest.findUrlByShortLink("test");

        Assertions.assertEquals(url, test);

    }
}