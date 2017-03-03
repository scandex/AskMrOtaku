package com.scandex.askmrotaku;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
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

		// List<Anime> an = recommender.getAnimeToReview();
		//
		// for (int i = 0; i < 10; i++) {
		// System.out.println(an.get(i).getName());
		// }

		long id = recommender.getTemporalId("a");
		System.out.println(id);

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
		recommender.setPreference(id, 30276, 10f);

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
