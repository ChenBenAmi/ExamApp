package com.example.examapp.data.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {


  @GET("androidexam.json")
  Call<List<JsonHero>> getHeroes();
}