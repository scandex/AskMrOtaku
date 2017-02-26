package com.scandex.askmrotaku;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.scandex.askmrotaku.domain.Anime;
import com.scandex.askmrotaku.domain.AnimeRepository;
import com.scandex.askmrotaku.service.IRecommendationService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimeRepositoryTest {

	@Autowired
	AnimeRepository repository;

	@Autowired
	ApplicationContext context;

	@Autowired
	IRecommendationService recommender;

	@Test
	public void readsFirstPageCorrectly() {
		
		List<Anime> an = recommender.getAnimeToReview();
		
		for (int i = 0; i < 10; i++) {
			System.out.println(an.get(i).getName());
		}

		long id = recommender.getTemporalId();

		recommender.setPreference(id, 17074, 10f);
		recommender.setPreference(id, 31757, 10f);
		recommender.setPreference(id, 31181, 10f);
		recommender.setPreference(id, 28297, 6f);
		recommender.setPreference(id, 20, 6f);
		recommender.setPreference(id, 30, 8f);
		recommender.setPreference(id, 5081, 10f);
		recommender.setPreference(id, 30015, 10f);
		recommender.setPreference(id, 14227, 10f);
		recommender.setPreference(id, 30276, 8f);

		List<Anime> recommendations = recommender.recommendUserBased(id);
		System.out.println("USER");
		for (Anime a : recommendations) {			
				System.out.println(a.getName() + ":" + a.getPrediction());
		}

		List<Anime> recom = recommender.recommendC1Based(id);
		System.out.println("C1");
		for (Anime a : recom) {
				System.out.println(a.getName() + ":" + a.getPrediction());
		}
		recom = recommender.recommendC2Based(id);
		System.out.println("C2");
		for (Anime a : recom) {
				System.out.println(a.getName() + ":" + a.getPrediction());
		}
	}
}
