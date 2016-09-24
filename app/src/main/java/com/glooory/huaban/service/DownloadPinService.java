package com.glooory.huaban.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.glooory.huaban.R;
import com.glooory.huaban.httputils.PinDownloadCallback;
import com.glooory.huaban.httputils.PinResponseBody;
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
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int NOTIFY_ID = 1001;
    private int preProgress = 0;

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

//        initNotification();
        new Retrofit.Builder()
                .baseUrl(getString(R.string.urlImageRoot))
                .client(initOkHttpClient())
                .build()
                .create(PinDownloadService.class)
                .httpForDownload(mPinUrlKey)
                .enqueue(new PinDownloadCallback(mPinDirPath, mPinName) {
                    @Override
                    public void onSuccess(File file) {
                        Toast.makeText(getApplicationContext(), "图片已保存到 " + mPinDirPath, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoading(long progress, long total) {
//                        updateNotification(progress * 100 / total);
//                        cancelNotification();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        cancelNotification();
                        Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 初始化通知
     */
    private void initNotification() {

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_petal_icon_24dp)
                .setContentText("0%")
                .setContentTitle("正在下载图片")
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());

    }

    private OkHttpClient initOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new PinResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();

    }

    /**
     * 更新下载进度
     * @param progress
     */
    private void updateNotification(long progress) {

        int currentProgress = (int) progress;
        if (preProgress < currentProgress) {
            builder.setContentText(currentProgress + "%");
            builder.setProgress(100, currentProgress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = currentProgress;

    }

    private void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }

    public interface PinDownloadService {

        //http://img.hb.aicdn.com/key
        @GET("/{key}")
        Call<ResponseBody> httpForDownload(@Path("key") String key);

    }
}
