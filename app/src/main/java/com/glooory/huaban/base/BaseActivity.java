package com.glooory.huaban.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.glooory.huaban.R;
import com.glooory.huaban.module.login.LoginActivity;
import com.glooory.huaban.user.UserSingleton;
import com.glooory.huaban.util.Base64;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.NetworkUtils;
import com.glooory.huaban.util.SPUtils;
import com.umeng.analytics.MobclickAgent;

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

    public int mScreenWidthPixels;

    //用户是否登录
    public boolean isLogin = false;
    //https联网字段
    public String mAuthorization;

    private CompositeSubscription mCompositeSubscription;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpTransition();
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
        mScreenWidthPixels = this.getResources().getDisplayMetrics().widthPixels;
        getNecessaryData();
        initResAndListener();
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
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

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = (boolean) SPUtils.get(getApplicationContext(), Constant.ISLOGIN, Boolean.FALSE);
        mAuthorization = getAuthorizations(isLogin);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //fresco shared element transition 已经解决的bug 调用以下方法
    private void setUpTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.CENTER_CROP));
        }
    }

    //提示登录
    public void showLoginSnackbar(final Activity activity, CoordinatorLayout coordinatorLayout) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.need_login, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.go_to_login, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.launch(activity);
            }
        }).show();
    }

    public void finishSelf() {
        if (Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
                return true;
            } else {
                super.onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
