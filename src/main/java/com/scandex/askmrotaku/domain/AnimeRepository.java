package com.scandex.askmrotaku.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String>{
	
	Anime findByAnimeId(int id);
}
