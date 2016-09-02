package com.glooory.huaban.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class IntentUtils {

    public static Intent startUriLink(String link) {
        Uri uri = Uri.parse(link);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static boolean checkResolveIntent(Activity activity, Intent intent) {
        //检查是否有组件响应我们的intent
        return intent.resolveActivity(activity.getPackageManager()) != null;
    }

    //打开图片的intent
    public static Intent startImageFile(File file, String type) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }
}
