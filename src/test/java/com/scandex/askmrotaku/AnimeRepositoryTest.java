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

//      List<Anime> animes = repository.findAll();
//      System.out.println(animes.get(0).getName());
//      System.out.println(animes.get(0).getId());
//    	recommender.recommend();
      
    }

}
