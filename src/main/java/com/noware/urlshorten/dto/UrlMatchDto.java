package com.noware.urlshorten.dto;

import com.noware.urlshorten.validation.ActiveUrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlMatchDto {

    @NotBlank(message = "Original URL is mandatory")
    @ActiveUrl
    private String longUrl;

    @NotBlank(message = "Desired URL is mandatory")
    @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Only letters, numbers and character _ - are allowed")
    private String shortUrl;

    private String message;
}
