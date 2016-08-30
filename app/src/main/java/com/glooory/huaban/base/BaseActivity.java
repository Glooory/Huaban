package com.glooory.huaban.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.glooory.huaban.R;
import com.glooory.huaban.user.UserSingleton;
import com.glooory.huaban.util.Base64;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.NetworkUtils;
import com.glooory.huaban.util.SPUtils;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getTAG();

    protected abstract int getLayoutId();

    protected abstract String getTAG();

    protected Context mContext;

    //用户是否登录
    public boolean isLogin = false;
    //https联网字段
    public String mAuthorization;

    protected static final int[] colorInts = new int[]{R.color.pink_300, R.color.pink_500,
            R.color.pink_700, R.color.pink_900};

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    //CompositeSubscription:
    //Subscription that represents a group of Subscriptions that are unsubscribed together.
    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }


    public void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //对于大于4.4的系统，动态设置状态栏为透明状态
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isTranslucentStatusBar()) {
                Window window = getWindow();
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        getNecessaryData();
        initResAndListener();
        if (isLogin) {
            Logger.d(TAG);
        }
    }

    //由子类继承，初始化监听器和定义显示资源
    protected void initResAndListener() {
    }

    protected void getNecessaryData() {
        UserSingleton.getInstance().isLogin(getApplication());
        isLogin = (boolean) SPUtils.get(mContext, Constant.ISLOGIN, false);
        mAuthorization = getAuthorizations(isLogin);
    }

    //状态栏是否为透明
    protected boolean isTranslucentStatusBar() {
        return true;
    }

    protected boolean isLogin() {
        return isLogin;
    }

    protected String getAuthorizations(boolean isLogin) {

        String temp = " ";
        if (isLogin) {
            return SPUtils.get(mContext, Constant.TOKENTYPE, temp)
                    + temp
                    + SPUtils.get(mContext, Constant.TOKENACCESS, temp);
        }
        return Base64.CLIENTINFO;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    protected void checkException(Throwable e, View rootView) {
        NetworkUtils.checkHttpException(mContext, e, rootView);
    }
}
