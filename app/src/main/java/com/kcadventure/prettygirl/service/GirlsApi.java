package com.kcadventure.prettygirl.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GirlsApi {

  @GET("ajax/load.php?action=load_all&page={id}")
  Observable<String> rawHtml(@Path("id") int id);

  @GET("ajax/load.php?action=load_all&page=2")
  Observable<String> rawHtml22();
}
