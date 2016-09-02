package com.glooory.huaban.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Glooory on 2016/9/2 0002.
 */
public class TimeUtils {

    private static final String TAG = "TimeUtils";

    public static final int SECOND = 1000;
    public static final int MINUTE = SECOND * 60;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;
    public static final int WEEK = DAY * 7;

    /**
     * 返回用户距上次登录的时间
     * @param oldTime
     * @param currentTime
     * @return
     */
    public static String getTimeDifference(int oldTime, long currentTime) {

        long hTime = ((long) oldTime) * 1000;
        long dTime = currentTime - hTime;

        if (dTime < MINUTE) {
            return dTime / SECOND + " 秒前";
        } else if (dTime < HOUR) {
            return dTime / MINUTE + " 分前";
        } else if (dTime < DAY) {
            return dTime / HOUR + " 小时前";
        } else if (dTime < WEEK) {
            return dTime / DAY + " 天前";
        } else {
            return DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).format(new Date(hTime));
        }

    }

}
