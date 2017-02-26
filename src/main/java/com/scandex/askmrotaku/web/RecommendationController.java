package com.scandex.askmrotaku.web;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scandex.askmrotaku.domain.Anime;
import com.scandex.askmrotaku.domain.AnimeRepository;
import com.scandex.askmrotaku.service.IRecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	private IRecommendationService recommender;	
	
	
	@RequestMapping("/userBased")
	public List<Anime> recommendUserBased(@RequestParam(value = "user_id") long id) {
				return recommender.recommendUserBased(id);
	}
	
	@RequestMapping("/cluster1Based")
	public List<Anime> recommendCluster1Based(@RequestParam(value = "user_id") long id) {
				return recommender.recommendC1Based(id);
	}
	
	@RequestMapping("/cluster2Based")
	public List<Anime> recommendCluster2Based(@RequestParam(value = "user_id") long id) {
				return recommender.recommendC2Based(id);
	}
	
	@RequestMapping("/animeToReview")
	public List<Anime> recommendCluster2Based() {
				return recommender.getAnimeToReview();
	}

	@RequestMapping("/preference")
	public void registerPreference(@RequestParam(value = "user_id") long userId,@RequestParam(value = "anime_id") long animeId,@RequestParam(value = "rating") double rating) {
		recommender.setPreference(userId, animeId, rating);
	}

	@RequestMapping("/getTempId")
	public long getTemporalId() {
		return recommender.getTemporalId();
	}
	
	@RequestMapping("/releaseId")
	public void releaseId(@RequestParam(value = "user_id") long id) {
				recommender.releaseTemporalId(id);
	}

}
