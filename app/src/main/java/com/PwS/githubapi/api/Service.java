package com.PwS.githubapi.api;

import com.PwS.githubapi.model.Item;
import com.PwS.githubapi.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by PwS
 */
public interface Service {

    @GET("users?")
    Call<List<Item>> getItems();

    @GET("search/users")
    Call <Users>  getSearchUser(@Query("q") String keyword);

   /*@GET("/users/username")
    Call<List<Item>> getSearchUser (@Path("username") String keyword);*/

  /* @GET("users?")
    Call<List<List>> getUsers(@Query("per_page") int perPage,@Query("page") int page);*/

}
