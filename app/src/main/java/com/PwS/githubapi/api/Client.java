package com.PwS.githubapi.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PwS
 */
public class Client {

    public static final String GITHUB_BASE_URL = "https://api.github.com";

    public static Retrofit build(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(GITHUB_BASE_URL);
        //GsonConverterFactory
        builder.addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        return retrofit;
    }

}
