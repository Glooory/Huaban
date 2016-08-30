package com.glooory.huaban.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Glooory on 2016/8/29 0029.
 */
public final class CompatUtils {

    public static Drawable getTintListDrawable(Context context, int resDrawableId, int resTintId) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resDrawableId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, resTintId));
        return drawable;
    }

    public static Drawable getTintDrawable(Context context, int resDrawableId, @ColorInt int tint) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, resDrawableId).mutate());
        DrawableCompat.setTint(drawable, tint);
        return drawable;
    }

}
