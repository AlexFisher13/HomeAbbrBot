package com.bot.repo;

import com.bot.entity.Abbreviation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AbbreviationRepo extends CrudRepository<Abbreviation, Integer> {
    Optional<List<Abbreviation>> findByAbbr(String abbr);
}

