package com.scandex.askmrotaku.service;

import java.util.List;

import org.apache.mahout.cf.taste.model.PreferenceArray;
import com.scandex.askmrotaku.domain.Anime;

public interface IRecommendationService {
	List<Anime> recommendUserBased(long userId);

	List<Anime> recommendC1Based(long userId);

	List<Anime> recommendC2Based(long userId);

	List<Anime> getAnimeToReview(long userId);

	void setPreference(long userId, long animeId, double value);

	long getTemporalId(String string);

	void releaseTemporalId(long id);

	PreferenceArray getPreferences(long id);

}
