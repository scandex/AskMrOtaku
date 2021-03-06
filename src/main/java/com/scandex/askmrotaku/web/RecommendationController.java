package com.scandex.askmrotaku.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scandex.askmrotaku.domain.Anime;

import com.scandex.askmrotaku.service.IRecommendationService;

@RestController
@RequestMapping("/recommendation")
@CrossOrigin(origins = "*")
public class RecommendationController {

	@Autowired
	private IRecommendationService recommender;

	@RequestMapping("/userBased/{userId}")
	public List<Anime> recommendUserBased(@PathVariable long userId) {
		return recommender.recommendUserBased(userId);
	}

	@RequestMapping("/cluster1Based/{userId}")
	public List<Anime> recommendCluster1Based(@PathVariable long userId) {
		return recommender.recommendC1Based(userId);
	}

	@RequestMapping("/cluster2Based/{userId}")
	public List<Anime> recommendCluster2Based(@PathVariable long userId) {
		return recommender.recommendC2Based(userId);
	}

	@RequestMapping("/animeToReview/{userId}")
	public List<Anime> getAnimeToReview(@PathVariable long userId) {
		return recommender.getAnimeToReview(userId);
	}

	@RequestMapping("/preference/{userId}")
	public void registerPreference(@PathVariable long userId, @RequestParam(value = "anime_id") long animeId,
			@RequestParam(value = "rating") double rating) {
		recommender.setPreference(userId, animeId, rating);
	}

	@RequestMapping("/getTempId")
	public long getTemporalId(HttpServletRequest request) {
		long resp = recommender.getTemporalId(request.getRemoteAddr());
		return resp;
	}

	@RequestMapping("/releaseId/{userId}")
	public void releaseId(@PathVariable long userId) {
		recommender.releaseTemporalId(userId);
	}
}
