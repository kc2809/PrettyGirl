package com.kcadventure.prettygirl.service;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GirlApiService {

  private static final String BASE_URL = "https://www.pic-th.com/";
  private GirlsApi girlsApi;

  public GirlApiService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    girlsApi = retrofit.create(GirlsApi.class);
  }

  public Observable<String> getRawHtmlAllGirls(int id) {
    return girlsApi.rawHtml(id);
  }

  public Observable<String> getRawHtmlAllGirls222() {
    return girlsApi.rawHtml22();
  }
}
