package com.scandex.askmrotaku.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService implements IRecommendationService {

	private DataModel model;
	private PlusAnonymousConcurrentUserDataModel plusModel;
	private Recommender itemBased;
	private Recommender userBased;

	// @Autowired

	public RecommendationService() {

		try {
			DataModel model = new FileDataModel(new File("data/rating.csv"));
			int concurrentUsers = 100;
			plusModel = new PlusAnonymousConcurrentUserDataModel(model, concurrentUsers);

			ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(plusModel);
			UserSimilarity userSimilarity = new TanimotoCoefficientSimilarity(plusModel);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, userSimilarity, plusModel);

			itemBased = new GenericItemBasedRecommender(plusModel, itemSimilarity);
			userBased = new GenericUserBasedRecommender(plusModel, neighborhood, userSimilarity);

			// concurrentUsers);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	public List<RecommendedItem> recommendUserBased(long userId) {
		List<RecommendedItem> resp = null;
		try {
			resp = userBased.recommend(userId, 100);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public List<RecommendedItem> recommendItemBased(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecommendedItem> recommendC1Based(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecommendedItem> recommendC2Based(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreference(long userId, long animeId, double value) {
		List<Preference> preferences = new ArrayList<Preference>();

	}

	@Override
	public long getTemporalId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void releaseTemporalId(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PreferenceArray getPreferences(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
