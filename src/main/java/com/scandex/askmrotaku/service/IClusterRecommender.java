package com.scandex.askmrotaku.service;

import java.util.List;

import org.apache.mahout.cf.taste.model.Preference;

import com.scandex.askmrotaku.domain.Anime;

public interface IClusterRecommender {
	List<Anime> recommendC1(List<Preference> preferences);

	List<Anime> recommendC2(List<Preference> preferences);
}
