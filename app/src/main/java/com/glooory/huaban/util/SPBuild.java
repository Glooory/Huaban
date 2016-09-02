package com.glooory.huaban.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class SPBuild {
    private final SharedPreferences.Editor editor;

    public SPBuild(Context context) {
        this.editor = context.getSharedPreferences(SPUtils.SP_FILE_NAME, SPUtils.SP_RW_MODE).edit();
    }

    public SPBuild addData(String key, Object object) {
        SPUtils.putAdd(editor, key, object);
        return this;
    }

    public void build() {
        this.editor.apply();
    }
}
