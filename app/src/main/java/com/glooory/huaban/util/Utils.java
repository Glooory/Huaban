package com.glooory.huaban.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Glooory on 2016/8/29 0029.
 */
public final class Utils {

    /**
     * 检查对象为非空
     * @param objece
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T chechNotNull(T objece, String message) {
        if (objece == null) {
            throw new NullPointerException(message);
        }
        return objece;
    }

    public static float getAspectRatio(int width, int height) {
        float ratio = (float) width / (float) height;
        //宽高比小于0.7即为长图，需要截断处理
        if (ratio < 0.7) {
            return 0.7f;
        }
        return ratio;
    }

    /**
     * 检查当前线程是否是主线程， 是则返回true
     * @return
     */
    public static boolean checkUIThreadBoolean() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 检查是否在UI线程 不是则抛出异常
     */
    public static void checkUITheadError() {
        if (!checkUIThreadBoolean()) {
            throw new IllegalStateException("must be called in UI Thread");
        }
    }

    /**
     * 检查图片是否为Gif图片
     * @param type
     * @return
     */
    public static boolean checkIsGif(String type) {
        if (type == null || TextUtils.isEmpty(type)) {
            return false;
        }
        if (type.contains("gif") || type.contains("GIF") || type.contains("Gif")) {
            return true;
        }
        return false;
    }

    /**
     * 转换为k表示
     * @param count
     * @return
     */
    public static String checkIfNeedConvert(int count) {
        if (count > 9999) {
            int i = count / 1000;
            int f = (count / 100) % 10;
            return String.valueOf(i) + "." + String.valueOf(f)  + "K";
        } else {
            return String.valueOf(count);
        }
    }

    /**
     * 返回图片的后缀
     * @param type
     * @return
     */
    public static String getPinType(String type) {

        if (type.contains("png")) {
            return ".png";
        }

        if (type.contains("gif")) {
            return ".gif";
        }

        return ".jpeg";

    }

    /**
     * 将dp转换为dx
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Point getPoint(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static int getScreenWidth(Context context) {
        return getPoint(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getPoint(context).y;
    }

}
