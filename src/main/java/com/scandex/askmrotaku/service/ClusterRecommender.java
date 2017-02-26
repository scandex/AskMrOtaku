package com.scandex.askmrotaku.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.math.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scandex.askmrotaku.domain.Anime;
import com.scandex.askmrotaku.domain.AnimeRepository;

@Service
public class ClusterRecommender implements IClusterRecommender {

	@Autowired
	private AnimeRepository repository;

	private List<Anime> animeCluster1;
	private List<Anime> animeCluster2;

	@PostConstruct
	public void start() {
		animeCluster1 = new ArrayList<Anime>();
		animeCluster2 = new ArrayList<Anime>();
		PageRequest request = new PageRequest(0, 100, new Sort(Sort.Direction.DESC, "rating"));
		for (int i = 1; i < 42; i++) {
			if (i < 41)
				animeCluster2.addAll(repository.findByCluster2(i, request));
			animeCluster1.addAll(repository.findByCluster1(i, request));

		}
	}

	public List<Anime> recommendC1(List<Preference> preferences) {
		List<Anime> resp = new ArrayList<Anime>(animeCluster1);
		double[] prefClusters = preferenceVector(preferences, 41);
		for (Anime anime : resp) {
			double prediction = prefClusters[anime.getCluster1() - 1] * anime.getRating();
			anime.setPrediction(prediction);
		}
		System.out.println(Arrays.toString(prefClusters));
		Collections.sort(resp);
		return resp.subList(0, 99);
	}

	public List<Anime> recommendC2(List<Preference> preferences) {
		List<Anime> resp = new ArrayList<Anime>(animeCluster2);
		double[] prefClusters = preferenceVector(preferences, 40);
		for (Anime anime : resp) {
			double prediction = prefClusters[anime.getCluster2() - 1] * anime.getRating();
			anime.setPrediction(prediction);
		}
		System.out.println(Arrays.toString(prefClusters));
		Collections.sort(resp);
		return resp.subList(0, 99);
	}

	public double[] preferenceVector(List<Preference> preferences, int size) {
		double[][] tmp = new double[size][2];
		double[] prefClusters = new double[size];
		for (Preference preference : preferences) {
			Anime a = repository.findByAnimeId(preference.getItemID());
			int cluster = a.getCluster1() - 1;
			if (size == 40)
				cluster = a.getCluster2() - 1;
			tmp[cluster][0]++;
			tmp[cluster][1] += preference.getValue();
		}
		double max = 0;
		for (int j = 0; j < size; j++) {
			prefClusters[j] = (tmp[j][0] * tmp[j][1]) / (double) preferences.size();
			if (prefClusters[j] > max)
				max = prefClusters[j];
		}
		for (int i = 0; i < prefClusters.length; i++) {
			prefClusters[i] = prefClusters[i] / max;
		}
		return prefClusters;
	}

}
