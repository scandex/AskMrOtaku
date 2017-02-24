package com.scandex.askmrotaku.web;

import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scandex.askmrotaku.service.IRecommendationService;

@RestController
public class RecommendationController {

	@Autowired
	private IRecommendationService recommender;

	@RequestMapping("/recommend")
	public List<RecommendedItem> recommendItemBased(@RequestParam(value = "user_id") long id,
			@RequestParam(value = "type") int type) {
		switch (type) {
			case 1: return recommender.recommendItemBased(id);
			case 2: return recommender.recommendUserBased(id);
			default:return null;
		}		
	}

	@RequestMapping("/preference")
	public void registerPreference(@RequestParam(value = "user_id") long userId,@RequestParam(value = "anime_id") long animeId,@RequestParam(value = "rating") double rating) {
		recommender.setPreference(userId, animeId, rating);
	}

	@RequestMapping("/get_temp_id")
	public long getTemporalId() {
		return recommender.getTemporalId();
	}

}
