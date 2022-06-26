package com.noware.urlshorten.entities;

import com.noware.urlshorten.validation.ActiveUrl;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UrlMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String longUrl;
    
    private String shortUrl;
}
