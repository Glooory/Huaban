package com.glooory.huaban.util;

import android.os.Looper;
import android.text.TextUtils;

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

}
