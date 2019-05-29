package com.example.examApp.data.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Api interface
 */
public interface ApiInterface {


  @GET("androidexam.json")
  Call<List<JsonHero>> getHeroes();
}