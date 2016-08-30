package com.glooory.huaban.user;

import android.app.Application;

import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class UserSingleton {

    private String mAuthorization;

    private Boolean isLogin;

    private volatile static UserSingleton instance = new UserSingleton();

    private UserSingleton() {
    }

    public static UserSingleton getInstance() {
        return instance;
    }

    public String getAuthorization() {
        if (isLogin) {
            if (mAuthorization == null) {

            }
            return mAuthorization;
        }

        return mAuthorization;
    }

    public void setAuthorization(String authorization) {
        this.mAuthorization = authorization;
    }

    public boolean isLogin(Application context) {
        if (isLogin == null) {
            isLogin = (boolean) SPUtils.get(context, Constant.ISLOGIN, false);
        }
        return isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
    }

}
