package com.scandex.askmrotaku.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scandex.askmrotaku.domain.Anime;
import com.scandex.askmrotaku.domain.AnimeRepository;

@Service
public class RecommendationService implements IRecommendationService {

	private PlusAnonymousConcurrentUserDataModel plusModel;

	private UserBasedRecommender userBased;
	private UserBasedRecommender userBased2;
	private Map<Long, List<Preference>> tempPrefs;
	private Map<String, Long> sessions;

	@Autowired
	private IClusterRecommender clusterRecommender;

	@Autowired
	private AnimeRepository repo;

	// @Autowired

	public RecommendationService() {

		try {
			DataModel model = new FileDataModel(new File("data/rating.csv"));
			int concurrentUsers = 100;
			plusModel = new PlusAnonymousConcurrentUserDataModel(model, concurrentUsers);

			UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(plusModel);
			UserSimilarity userSimilarity2 = new TanimotoCoefficientSimilarity(plusModel);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, userSimilarity, plusModel);
			UserNeighborhood neighborhood2 = new ThresholdUserNeighborhood(0.1, userSimilarity2, plusModel);

			userBased = new GenericUserBasedRecommender(plusModel, neighborhood, userSimilarity);
			userBased2 = new GenericUserBasedRecommender(plusModel, neighborhood2, userSimilarity2);
			tempPrefs = new HashMap<Long, List<Preference>>();
			sessions = new HashMap<String, Long>();

			// concurrentUsers);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public List<Anime> recommendUserBased(long userId) {
		List<Anime> resp = new ArrayList<Anime>();
		try {
			if (tempPrefs.containsKey(userId)) {
				List<Preference> preferences = tempPrefs.get(userId);
				PreferenceArray tempPreferences = new GenericUserPreferenceArray(preferences);
				plusModel.setTempPrefs(tempPreferences, userId);
				System.out.println(plusModel.getPreferencesFromUser(userId));
				List<RecommendedItem> temp = userBased.recommend(userId, 100);
				if (temp.isEmpty())
					temp = userBased2.recommend(userId, 100);
				for (RecommendedItem recommendedItem : temp) {
					Anime anime = repo.findByAnimeId(recommendedItem.getItemID());
					anime.setPrediction(recommendedItem.getValue());
					resp.add(anime);
				}
			}
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public List<Anime> recommendC1Based(long userId) {
		// TODO Auto-generated method stub
		List<Anime> resp = new ArrayList<Anime>();
		if (tempPrefs.containsKey(userId)) {
			List<Preference> preferences = tempPrefs.get(userId);
			resp = clusterRecommender.recommendC1(preferences);
		}
		return resp;
	}

	@Override
	public List<Anime> recommendC2Based(long userId) {
		// TODO Auto-generated method stub
		List<Anime> resp = new ArrayList<Anime>();
		if (tempPrefs.containsKey(userId)) {
			List<Preference> preferences = tempPrefs.get(userId);
			resp = clusterRecommender.recommendC2(preferences);
		}
		return resp;
	}

	@Override
	public void setPreference(long userId, long animeId, double value) {
		List<Preference> preferences = tempPrefs.get(userId);
		boolean stop = false;
		if (preferences == null) {
			preferences = new ArrayList<Preference>();
			tempPrefs.put(userId, preferences);
		}
		for (int i = 0; i < preferences.size() && !stop; i++) {
			if (preferences.get(i).getItemID() == animeId) {
				preferences.remove(i);
				stop = true;
			}
		}
		preferences.add(new GenericPreference(userId, animeId, (float) value));
	}

	@Override
	public long getTemporalId(String remote) {
		// TODO Auto-generated method stub
		long resp;
		if (sessions.containsKey(remote)) {
			resp = sessions.get(remote);
		} else {
			resp = plusModel.takeAvailableUser();
			sessions.put(remote, resp);
		}
		return resp;
	}

	@Override
	public void releaseTemporalId(long id) {
		// TODO Auto-generated method stub
		tempPrefs.remove(id);
		plusModel.releaseUser(id);
	}

	@Override
	public PreferenceArray getPreferences(long id) {
		// TODO Auto-generated method stub
		try {
			List<Preference> preferences = tempPrefs.get(id);
			PreferenceArray tempPrefs = new GenericUserPreferenceArray(preferences);
			plusModel.setTempPrefs(tempPrefs, id);
			return plusModel.getPreferencesFromUser(id);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Anime> getAnimeToReview(long userId) {
		List<Anime> resp = repo.getAnimeToReview();
		if (tempPrefs.containsKey(userId)) {
			List<Preference> prefs = tempPrefs.get(userId);
			for (Preference preference : prefs) {
				boolean stop = false;
				for (int j = 0; j < resp.size() && !stop; j++) {
					if (resp.get(j).getAnimeId() == preference.getItemID()) {
						resp.remove(j);
						stop = true;
					}
				}

			}
		}
		Collections.shuffle(resp);
		return resp;
	}

}
