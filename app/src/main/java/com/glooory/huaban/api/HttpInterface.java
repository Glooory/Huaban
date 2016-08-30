package com.glooory.huaban.api;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public abstract class HttpInterface<T> {

    //联网成功，数据返回成功
    public abstract void onHttpSuccess(T result);

    //联网成功，数据返回失败
    public abstract void onHttpError(int code, String message);

    //网络连接失败
    public abstract void onHttpFailure(String error);

    public void onHttpStart() {

    }

    public void onHttpFinish() {

    }

}
