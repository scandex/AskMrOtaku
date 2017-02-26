package com.scandex.askmrotaku.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AnimeRepository extends MongoRepository<Anime, String>{
	
	
	Anime findByAnimeId(long id);
	@Query("{ 'c1':?0 ,'type' : 'TV' }")
	List<Anime> findByCluster1(int cluster1, Pageable pageable);
	@Query("{ 'c2':?0 ,'type' : 'TV' }")
	List<Anime> findByCluster2(int cluster2, Pageable pageable);
	@Query("{ 'type': 'TV', 'members': { $gt: 100000 }, 'rating': { $gt: 0.0 } }")
	List<Anime> getAnimeToReview();
	
}
