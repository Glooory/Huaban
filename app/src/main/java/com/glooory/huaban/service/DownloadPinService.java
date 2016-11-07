package com.glooory.huaban.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import com.glooory.huaban.R;
import com.glooory.huaban.net.FileDownloadCallback;
import com.glooory.huaban.net.FileResponseBody;
import com.glooory.huaban.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Glooory on 2016/9/13 0013 14:12.
 */
public class DownloadPinService extends IntentService {
    private static final String KEY_URL_KEY = "key_url_key";
    private static final String KEY_TYPE = "key_type";
    private String mPinName;
    private String mPinDirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "huaban";
    private String mPinUrlKey;

    public static void launch(Activity activity, String urlKey, String type) {
        Intent intent = new Intent(activity, DownloadPinService.class);
        intent.putExtra(KEY_URL_KEY, urlKey);
        intent.putExtra(KEY_TYPE, type);
        activity.startService(intent);
    }

    public DownloadPinService() {
        super("DownloadPinService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mPinUrlKey = intent.getExtras().getString(KEY_URL_KEY);
        String pinType = intent.getExtras().getString(KEY_TYPE);
        mPinName = String.valueOf(System.currentTimeMillis()) + Utils.getPinType(pinType);
        actionDownload();
    }

    /**
     * 开始下载
     */
    private void actionDownload() {
        new Retrofit.Builder()
                .baseUrl(getString(R.string.urlImageRoot))
                .client(initOkHttpClient())
                .build()
                .create(PinDownloadService.class)
                .httpForDownload(mPinUrlKey)
                .enqueue(new FileDownloadCallback(mPinDirPath, mPinName) {
                    @Override
                    public void onSuccess(File file) {
                        Toast.makeText(getApplicationContext(), "图片已保存到 " + mPinDirPath, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private OkHttpClient initOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20000, TimeUnit.MILLISECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();

    }

    public interface PinDownloadService {

        //http://img.hb.aicdn.com/key
//        @Streaming
//        The @Streaming declaration doesn't mean you're watching a Netflix file.
//        It means that instead of moving the entire file into memory,
//        it'll pass along the bytes right away.
//        But be careful, if you're adding the @Streaming declaration and continue to use the code above,
//        Android will trigger a android.os.NetworkOnMainThreadException.
//
//        Thus, the final step is to wrap the call into a separate thread, for example with a lovely ASyncTask

        @GET("/{key}")
        Call<ResponseBody> httpForDownload(@Path("key") String key);

    }
}
