package dev.jinkim.usercommentsdemo;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Jin on 2/4/15.
 */

public class RestClient {
    private ApiService apiService;
    private static String BASE_URL = "http://dev.m.gatech.edu/w/usercomments/c/api";
    private final boolean USE_PRODUCTION_ENDPOINT = true;
    private static String TAG = "RestClient";

    public RestClient() {

//        Gson gson = new GsonBuilder()
//                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
//                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
//                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public interface ApiService {

        // session should be formatted as sessionName=sessionId
        @GET("/comment")
        void listComments(@Header("Cookie") String session, Callback<List<Comment>> cb);
    }
}