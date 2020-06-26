package com.AZapps.retrofit;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    /* here we define the methods that we will use to get data from the server
    // Retrofit will take care of implementing this method but it  should be annotated
    // we can write all of the URL but we will define the base URL later so here we
    // define the relative URL after the base
    // in this example the full URL is https://jsonplaceholder.typicode.com/posts
    // the base (will not be changed) https://jsonplaceholder.typicode.com/
    // the relative is posts
    */
//
//    // normal GET request
//        @GET("posts")
//        Call<List<Post>> getPosts();


//    // Query GET request
//    // retrofit will take care of adding ? and = to be like this
//    // /posts?userId={userId} -----> /posts?userId=1
//    @GET("posts")
//    Call<List<Post>> getPosts(@Query("userId") int userId);


//    // add more than one query parameter
//    // the query will look like: /posts?userId=1&_sort=id&_order=desc
//    // assuming that we pass getPosts(1,id,desc) as parameter
//    // we user array of userIds as we do not know how many ids we will pass
//    // making Integer not int array because we can pass null to ignore this param
//    // but int values cannot be nullable so wrap it with Integer
//    @GET("posts")
//    Call<List<Post>> getPosts(
//            @Query("userId") Integer[] userId,
//            @Query("_sort") String sort,
//            @Query("_order") String order);

    // what if we want to pass all the prams and it's type when calling this method
    // we use QueryMap that we can pass pairs of what kind we need
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);


    ////  hardcoded path
    //    @GET("posts/1/comments")
    //    Call<List<Comment>> getComments();


//    // dynamic path
////    @GET("posts/{id}/comments")
////    Call<List<Comment>> getComments(@Path("id") int postId);

    // we can add the whole relative Url as a String
    // or the whole Url and it will replace the base Url here or at calling it
    @GET()
    Call<List<Comment>> getComments(@Url String url);

    // to add data to the server but the api we are using will not actually send any data
    // instead it will fake the response so we will post it and get it again to see if it is done
    // we add @body which is the data we want to send
    @POST("posts")
    Call<Post> createPost(@Body Post post);

//    // another way to send data to the server is by pass all the fields here
//    @FormUrlEncoded
//    @POST("posts")
//    Call<Post> createPost(
//            @Field("userId") int userId,
//            @Field("title") String title,
//            @Field("body") String body);


}
