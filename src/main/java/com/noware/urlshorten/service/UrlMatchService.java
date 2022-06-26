package com.noware.urlshorten.service;

import com.noware.urlshorten.dto.UrlMatchDto;
import com.noware.urlshorten.entities.UrlMatch;
import com.noware.urlshorten.repositories.UrlMatchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UrlMatchService {

    @Autowired
    private final UrlMatchRepository urlMatchRepository;

    @Value( "${host}" )
    private String host;

    public Iterable<UrlMatch> findAll() {
        return urlMatchRepository.findAll();
    }

    public UrlMatchDto saveUrl(UrlMatchDto urlMatchDto) {
        ModelMapper modelMapper = new ModelMapper();
        UrlMatch urlMatch = modelMapper.map(urlMatchDto, UrlMatch.class);

        //Check if there's already a short url registered with the same name:
        UrlMatchDto matchDto = getDto(urlMatch.getShortUrl(),
                "Short link: %s%s already existing, choose another one",
                (shortUrl) -> urlMatchRepository.findByShortUrl(shortUrl).orElse(null),
                modelMapper);
        if (matchDto!=null) {
            return matchDto;
        }
        //If a long url is already registered, we give to the user the existing short url:
        matchDto = getDto(urlMatch.getLongUrl(),
                "Long link already registered. You can use this short link: %s%s",
                (longUrl) -> urlMatchRepository.findByLongUrl(longUrl).orElse(null),
                modelMapper);
        if (matchDto!=null) {
            return matchDto;
        }
        //If there's no match, we save the new URLs pair:
        return getDto(urlMatch,
                "Short link created: %s%s",
                urlMatchRepository::save,
                modelMapper);
    }

    private <T> UrlMatchDto getDto(T param, String message, Function<T, UrlMatch> function, ModelMapper modelMapper) {
        UrlMatch alreadyExistingUrl = function.apply(param);
        if (alreadyExistingUrl!=null) {
            UrlMatchDto al = modelMapper.map(alreadyExistingUrl, UrlMatchDto.class);
            al.setMessage(String.format(message, host, al.getShortUrl()));
            return al;
        }
        return null;
    }

    public String findLongUrlFromShort(String shortUrl) {
        return urlMatchRepository.findByShortUrl(shortUrl).orElseThrow(
                () -> new IllegalArgumentException("Invalid shortUrl:" + shortUrl)).getLongUrl();
    }
}
