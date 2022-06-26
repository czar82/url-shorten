package com.noware.urlshorten.controllers;

import com.noware.urlshorten.dto.UrlMatchDto;
import com.noware.urlshorten.service.UrlMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UrlMatchController {

    private final UrlMatchService urlMatchService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("urlMatchDto", new UrlMatchDto());
        return "index";
    }

    @GetMapping
    public String base(Model model) {
        model.addAttribute("urlMatchDto", new UrlMatchDto());
        return "index";
    }

    /**
     * Redirect to desired URL, the regex is excluding dot to not catch icons requests from browser
     *
     * @param shortUrl      Shorten url
     * @return              Web page to desired long URL
     */
    @GetMapping("/{shortUrl:^(?:(?!\\.).)*$}")
    public String redirect(@PathVariable("shortUrl") String shortUrl) {
        String longUrl = urlMatchService.findLongUrlFromShort(shortUrl);
        return "redirect:https://" + longUrl;
    }

    @PostMapping("/create-shorten-link")
    public String addLink(@Valid UrlMatchDto urlMatchDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        if (urlMatchDto.getLongUrl().contains("https://")) {
            urlMatchDto.setLongUrl(urlMatchDto.getLongUrl().replace("https://", ""));
        }
        urlMatchDto = urlMatchService.saveUrl(urlMatchDto);
        model.addAttribute("urlMatchDto", urlMatchDto);
        return "index";
    }

}
