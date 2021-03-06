package com.glooory.huaban.module.welcome;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.glooory.huaban.R;
import com.glooory.huaban.api.LoginApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.net.RetrofitClient;
import com.glooory.huaban.module.login.LoginActivity;
import com.glooory.huaban.entity.login.TokenBean;
import com.glooory.huaban.module.main.MainActivity;
import com.glooory.huaban.rx.AnimOnSubscribe;
import com.glooory.huaban.util.Base64;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.EncrypAES;
import com.glooory.huaban.util.SPBuild;
import com.glooory.huaban.util.SPUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindString;
import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/2 0002.
 */
public class WelcomeActivity extends BaseActivity {

    //登录需要的报文
    private static final String PASSWORD = "password";
    private static int mTimeDifference;

    private boolean needRefreshToken;
    private boolean skipLogin;
    private EncrypAES mAES;

    @BindString(R.string.text_auto_login_fail)
    String mMessageFail;
    @BindView(R.id.img_welcome)
    ImageView mWelcomeImg;
    @BindView(R.id.img_welcome_logo)
    ImageView mWelcomeLogoImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected String getTAG() {
        return "WelcomeActivity";
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = (Boolean) SPUtils.get(getApplicationContext(),
                Constant.ISLOGIN, Boolean.FALSE);
        mAES = new EncrypAES();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Animator scaleAnimator = AnimatorInflater.loadAnimator(mContext, R.animator.welcome_img_scale_animator);
        Animator alphaAnimator = AnimatorInflater.loadAnimator(mContext, R.animator.welcome_logo_alpha_animator);

        scaleAnimator.setTarget(mWelcomeImg);
        alphaAnimator.setTarget(mWelcomeLogoImg);

        alphaAnimator.setStartDelay(500);
        alphaAnimator.start();


        Observable.create(new AnimOnSubscribe(scaleAnimator)) // 主线程， 由下一行subscribeOn()指定
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .filter(new Func1<Void, Boolean>() {
                    // IO线程， 由observeOn() 指定
                    @Override
                    public Boolean call(Void aVoid) {
                        skipLogin = (boolean) SPUtils.get(getApplicationContext(), Constant.ISSKIPLOGIN, Boolean.FALSE);
                        Logger.d("skipLogin:" + skipLogin);
                        return isLogin;
                    }
                })
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        mTimeDifference = (int) SPUtils.get(getApplicationContext(),
                               Constant.TOKENEXPIRESIN, 0);
//                        Logger.d(mTimeDifference);
                        Long lastLoginTime = (Long) SPUtils.get(getApplicationContext(), Constant.LOGINTIME, 0L);
                        Long differenceTime = (System.currentTimeMillis() - lastLoginTime) / 1000;
//                        Logger.d("lastLoginTime is:  " + lastLoginTime + "-----" + "differenceTime : " + differenceTime);
                        needRefreshToken =  differenceTime > mTimeDifference;
                        Logger.d("needRefredToken" + needRefreshToken);
                        return needRefreshToken;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Void, Observable<TokenBean>>() {
                    //IO线程， 由observeOn() 指定
                    @Override
                    public Observable<TokenBean> call(Void aVoid) {
                        String account = (String) SPUtils.get(getApplicationContext(), Constant.USERACCOUNT, "");
                        String passwordAES = (String) SPUtils.get(getApplicationContext(), Constant.USERPASSWORD, "");
                        String password = mAES.DecryptorString(passwordAES);
                        return getUserToken(account, password);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        if (isLogin) {
                                MainActivity.launch(WelcomeActivity.this);
                        } else {
                            if (skipLogin) {
                                MainActivity.launch(WelcomeActivity.this);
                            } else {
                                LoginActivity.launch(WelcomeActivity.this, true);
                            }
                        }
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Logger.d("onError    " + e.getMessage());
                        LoginActivity.launch(WelcomeActivity.this, mMessageFail, true);
                        finish();
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        Logger.d("onNext()   " + tokenBean.toString());
                        Logger.d("expire_in:" + tokenBean.getExpires_in());
                        saveToken(tokenBean);
                    }
                });
    }

    private void saveToken(TokenBean tokenBean) {
        Logger.d(tokenBean.toString());

        new SPBuild(getApplicationContext())
                .addData(Constant.LOGINTIME, System.currentTimeMillis())
                .addData(Constant.TOKENACCESS, tokenBean.getAccess_token())
                .addData(Constant.TOKENTYPE, tokenBean.getToken_type())
                .addData(Constant.TOKENEXPIRESIN, tokenBean.getExpires_in())
                .build();
    }

    private Observable<TokenBean> getUserToken(String account, String password) {

        return RetrofitClient.createService(LoginApi.class)
                .httpsTokenRx(Base64.CLIENTINFO, PASSWORD, account, password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
