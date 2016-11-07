package com.glooory.huaban.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Glooory on 2016/8/30 0030.
 */
public class RetrofitClient {

    private static final String mBaseUrl = "http://api.huaban.com/";

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build());

    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

}
