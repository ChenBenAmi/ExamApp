package com.example.examApp.data.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * @author Chen.
 * @version 1 at 30/5/2019.
 *Api singleton class main job is to build the retrofit client
 */
public class ApiHelper {

    private static ApiHelper instance = new ApiHelper();
    private static Retrofit retrofit;

    private ApiHelper() {

    }

    public static ApiHelper getInstance() {
        return instance;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://heroapps.co.il/employee-tests/android/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }

        return retrofit;
    }
}
