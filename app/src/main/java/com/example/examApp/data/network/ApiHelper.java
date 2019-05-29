package com.example.examApp.data.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
