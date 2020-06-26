package com.AZapps.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO : DO NOT FORGET TO ADD INTERNET PERMISSION
public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        // now here will will use retrofit and specify
        // 1-the base URL
        // 2-the converter which we will use (here we will use Gson not Json)

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // here we do not use (new) keyword instead we tell retrofit to implement all annotated methods
        // in the specified class (JsonPlaceHolderApi)
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        createPost();

    }

    private void getPosts() {
        // map params
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");
        // our method is now implemented by retrofit so we can use it
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        // we can call execute to preform this network operation but
        // we will get exception because we preform this network operation on the main thread
        // luckily retrofit takes care of that too be enqueue method
        call.enqueue(new Callback<List<Post>>() {
            // here we can check if the request is successfully sent or not
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                // but we ca not ensure that the data have been fetched
                // so we should check the response code we get if it is successful or not
                if (!response.isSuccessful()) {
                    // the response id not successful
                    textViewResult.setText("Code: " + response.code());
                    // now use else to write the code for the successful or use return and write it after
                    return;
                }
                // everything is fine now use .body() to get the result
                List<Post> posts = response.body();
                printPostData(posts);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/8/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText(response.code());
                    return;
                }
                List<Comment> comments = response.body();
                printCommentData(comments);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    //    private void createPost() {
    //        // here we send our data to the server
    //        Call<Post> call = jsonPlaceHolderApi.createPost(new Post(1, "new title", "new text"));
    //        call.enqueue(new Callback<Post>() {
    //            @Override
    //            public void onResponse(Call<Post> call, Response<Post> response) {
    //                if (!response.isSuccessful()) {
    //                    textViewResult.setText("Code: " + response.code());
    //                    return;
    //                }
    //                Post post = response.body();
    //
    //                String content = "";
    //                content += "Code: " + response.code() + "\n";
    //                content += "ID: " + post.getId() + "\n";
    //                content += "User ID: " + post.getUserId() + "\n";
    //                content += "Title: " + post.getTitle() + "\n";
    //                content += "Text: " + post.getText() + "\n\n";
    //                textViewResult.append(content);
    //
    //            }
    //
    //            @Override
    //            public void onFailure(Call<Post> call, Throwable t) {
    //                textViewResult.setText(t.getMessage());
    //            }
    //        });
    //    }

    private void createPost() {
        // here we send our data to the server
        // but using FormUrlEncoded

        Call<Post> call = jsonPlaceHolderApi.createPost(new Post(1,"new Title 2","new body 2"));

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                Post post = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + post.getId() + "\n";
                content += "User ID: " + post.getUserId() + "\n";
                content += "Title: " + post.getTitle() + "\n";
                content += "Text: " + post.getText() + "\n\n";
                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private void printPostData(List<Post> posts) {
        // this method to print the result in the textView
        // **********************it is only for testing****************************
        for (Post post : posts) {
            String content = "";
            content += "ID: " + post.getId() + "\n";
            content += "User ID: " + post.getUserId() + "\n";
            content += "Title: " + post.getTitle() + "\n";
            content += "Text: " + post.getText() + "\n\n";
            textViewResult.append(content);
        }
    }

    private void printCommentData(List<Comment> comments) {
        // this method to print the result in the textView
        // **********************it is only for testing****************************
        for (Comment comment : comments) {
            String content = "";
            content += "ID: " + comment.getId() + "\n";
            content += "Post ID: " + comment.getPostId() + "\n";
            content += "Name: " + comment.getName() + "\n";
            content += "Email: " + comment.getEmail() + "\n";
            content += "Text: " + comment.getText() + "\n\n";
            textViewResult.append(content);
        }
    }


}