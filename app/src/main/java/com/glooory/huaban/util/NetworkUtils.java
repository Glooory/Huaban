package com.glooory.huaban.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.glooory.huaban.R;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 弹出错误提示，并添加点击事件跳转到设置
     * @param context
     * @param view
     * @param message
     * @param action
     */
    public static void showNetworkErrorSnackbar(final Context context, View view, String message, String action) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        context.startActivity(intent);
                    }
                }).show();
    }

    public static Snackbar showSnackbar(View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        return snackbar;
    }

    /**
     * 判断网络错误类型
     * @param context
     * @param throwable
     * @param rootView
     */
    public static void checkHttpException(Context context, Throwable throwable, View rootView) {
        String snack_action_to_setting = context.getString(R.string.snack_action_to_setting);
        if (throwable instanceof UnknownHostException) {
            String snack_message_net_error = context.getString(R.string.snack_message_net_error);
            NetworkUtils.showNetworkErrorSnackbar(context, rootView, snack_message_net_error, snack_action_to_setting);
        } else if (throwable instanceof JsonSyntaxException) {
            String snack_message_data_error = context.getString(R.string.snack_message_data_error);
            NetworkUtils.showNetworkErrorSnackbar(context, rootView, snack_message_data_error, snack_action_to_setting);
        } else if (throwable instanceof SocketTimeoutException) {
            String snack_message_time_out = context.getString(R.string.snack_message_timeout_error);
            NetworkUtils.showNetworkErrorSnackbar(context, rootView, snack_message_time_out, snack_action_to_setting);
        } else if (throwable instanceof ConnectException) {
            String snack_message_net_error = context.getString(R.string.snack_message_net_error);
            NetworkUtils.showNetworkErrorSnackbar(context, rootView, snack_message_net_error, snack_action_to_setting);
        } else {
            String snack_message_unknown_error = context.getString(R.string.snack_message_unknown_error);
            NetworkUtils.showNetworkErrorSnackbar(context, rootView, snack_message_unknown_error, snack_action_to_setting);
        }
    }

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 跳转到网络设置界面
     * @param activity
     */
    public static void jumpToSettings(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
