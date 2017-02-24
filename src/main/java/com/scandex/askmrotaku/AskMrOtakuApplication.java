package com.scandex.askmrotaku;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.scandex.askmrotaku.domain.Anime;
import com.scandex.askmrotaku.domain.AnimeRepository;

@SpringBootApplication
public class AskMrOtakuApplication{
	
	@Autowired
	private AnimeRepository repository;	

	public static void main(String[] args) {
		SpringApplication.run(AskMrOtakuApplication.class, args);
	}
}
