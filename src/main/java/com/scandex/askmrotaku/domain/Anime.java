package com.scandex.askmrotaku.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Anime")
public class Anime {
	@Id
	private String id;

	@Field("name")
	private String name;

	@Field("anime_id")
	private long animeId;

	@Field("genre")
	private String genre;

	@Field("type")
	private String type;

	@Field("episodes")
	private int episodes;

	@Field("rating")
	private double rating;

	@Field("img")
	private String image;

	@Field("studios")
	private String studios;

	@Field("source")
	private String source;

	@Field("main_cast")
	private String cast;

	@Field("c1")
	private int cluster1;

	@Field("c2")
	private int cluster2;

	@Field("members")
	private int members;

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAnimeId() {
		return animeId;
	}

	public void setAnimeId(long animeId) {
		this.animeId = animeId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getCluster1() {
		return cluster1;
	}

	public void setCluster1(int cluster1) {
		this.cluster1 = cluster1;
	}

	public int getCluster2() {
		return cluster2;
	}

	public void setCluster2(int cluster2) {
		this.cluster2 = cluster2;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

}
