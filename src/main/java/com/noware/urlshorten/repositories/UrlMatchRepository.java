package com.noware.urlshorten.repositories;

import com.noware.urlshorten.entities.UrlMatch;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMatchRepository extends CrudRepository<UrlMatch, Long> {

    Optional<UrlMatch> findByShortUrl(String url);
    Optional<UrlMatch> findByLongUrl(String ulr);

}
