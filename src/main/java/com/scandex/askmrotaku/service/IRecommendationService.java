package com.scandex.askmrotaku.service;

import java.util.List;

import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public interface IRecommendationService {
	List<RecommendedItem> recommendUserBased(long userId);

	List<RecommendedItem> recommendItemBased(long userId);

	List<RecommendedItem> recommendC1Based(long userId);

	List<RecommendedItem> recommendC2Based(long userId);
	
	void setPreference(long userId, long animeId, double value);
	
	long getTemporalId();
	
	void releaseTemporalId(long id);
	
	PreferenceArray getPreferences(long id);

}
