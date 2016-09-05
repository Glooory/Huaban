package com.glooory.huaban.module.login;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.glooory.huaban.R;
import com.glooory.huaban.api.LoginApi;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.entity.BoardItemInfoBean;
import com.glooory.huaban.entity.BoardListInfoBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.main.MainActivity;
import com.glooory.huaban.util.Base64;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.IntentUtils;
import com.glooory.huaban.util.NetworkUtils;
import com.glooory.huaban.util.SPBuild;
import com.glooory.huaban.util.SPUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class LoginActivity extends BaseActivity {

    //登录时需要的报文
    private static final String PASSWORD = "password";
    private static final String TYPE_KEY = "type_key";

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar;
    @BindView(R.id.actv_username)
    AutoCompleteTextView mACTVUsername;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.progress_login)
    ProgressBar mProgressLogin;
    @BindView(R.id.ll_login)
    LinearLayout mLinearLogin;
    @BindView(R.id.scroll_login_form)
    ScrollView mScrollLoginForm;
    @BindView(R.id.btn_login_skip)
    Button mLoginSkipBtn;

    private TokenBean mTokenBean;
    private UserInfoBean mUserBean;

    //需要的资源
    @BindString(R.string.snack_message_login_success)
    String snackLoginSuccess;

    //联网的授权字段
    public String mAuthorization = Base64.CLIENTINFO;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, String message) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(TYPE_KEY, message);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String message = getIntent().getStringExtra(TYPE_KEY);
        if (!TextUtils.isEmpty(message)) {
            NetworkUtils.showSnackbar(mScrollLoginForm, message);
        }

        addUsernameAutoComplete();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        RxTextView.editorActions(mEditPassword, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_DONE;
            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        attemptLogin();
                    }
                });

        //登录按钮响应事件
        RxView.clicks(mBtnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        attemptLogin();
                    }
                });

        //注册按钮响应事件
        RxView.clicks(mBtnRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startNetRegister();
                    }
                });

        RxView.clicks(mLoginSkipBtn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        new SPBuild(getApplicationContext())
                                .addData(Constant.ISSKIPLOGIN, Boolean.TRUE)
                                .build();
                        MainActivity.launch(LoginActivity.this);
                        finish();
                    }
                });
    }

    private void startNetRegister() {
        //跳转到网页注册界面
        Intent intent = IntentUtils.startUriLink(getResources().getString(R.string.urlRegister));
        if (IntentUtils.checkResolveIntent(this, intent)) {
            startActivity(intent);
        } else {
            Logger.d("there is no browser");
        }
    }

    private void addUsernameAutoComplete() {
        //系统读入内容帮助用户输入用户名
        ArrayList<String> arraylist = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_dropdown_item, arraylist);

        mACTVUsername.setAdapter(adapter);
    }

    private void attemptLogin() {

        //清楚错误信息
        mACTVUsername.setError(null);
        mEditPassword.setError(null);

        //保存用户名和密码
        String username = mACTVUsername.getText().toString();
        String password = mEditPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //检查密码是否合法
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mEditPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEditPassword;
            cancel = true;
        }

        //检查用户名是否合法
        if (TextUtils.isEmpty(username)) {
            mACTVUsername.setError(getString(R.string.error_field_required));
            focusView = mACTVUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mACTVUsername.setError(getString(R.string.error_invalid_username));
            focusView = mACTVUsername;
            cancel = true;
        }

        //所有检查完成， 判断是开始联网登陆还是弹出错误提示
        if (cancel) {
            focusView.requestFocus();
        } else {
            httpLogin(username, password);
        }

    }

    private void httpLogin(final String username, final String password) {
        Logger.d("httpLogin() excuted");

        RetrofitClient.createService(LoginApi.class)
                .httpsTokenRx(mAuthorization, PASSWORD, username, password)
                //得到token成功， 用内部字段保存， 在最后得到用户信息是一起保存
                //得到Observable<> 将它转换为另一个Observable<>
                .flatMap(new Func1<TokenBean, Observable<UserInfoBean>>() {
                    @Override
                    public Observable<UserInfoBean> call(TokenBean tokenBean) {
                        Logger.d("获取token成功    " + tokenBean.toString());
                        mTokenBean = tokenBean;
                        mAuthorization = tokenBean.getToken_type() + " " + tokenBean.getAccess_token();
                        Logger.d(mAuthorization);
                        return RetrofitClient.createService(LoginApi.class)
                                .httpsUserRx(mAuthorization);
                    }
                })
                .flatMap(new Func1<UserInfoBean, Observable<BoardListInfoBean>>() {
                    @Override
                    public Observable<BoardListInfoBean> call(UserInfoBean userInfoBean) {
                        Logger.d("获取 UserInfoBean 成功    " + userInfoBean.getUsername());
                        mUserBean = userInfoBean;
                        return RetrofitClient.createService(UserApi.class)
                                .httpsBoardListInfo(mAuthorization, Constant.OPERATEBOARDEXTRA);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardListInfoBean>() {

                    @Override
                    public void onStart() {
                        Logger.d("观察者 start() excuted");

                        super.onStart();
                        showProgress(true);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        showProgress(false);
                        NetworkUtils.checkHttpException(mContext, e, mScrollLoginForm);
                    }

                    @Override
                    public void onNext(BoardListInfoBean boardListInfoBean) {
                        Logger.d("onNext() excuted" + boardListInfoBean.getBoards().size());
                        showProgress(false);
                        NetworkUtils.showSnackbar(mScrollLoginForm, snackLoginSuccess).setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                                // TODO: 2016/9/1 0001 Launch UserActivity
                                finish();
                            }
                        });
                        saveUserInfo(mUserBean, mTokenBean, username, password, boardListInfoBean.getBoards());
                    }
                });
    }

    private void saveUserInfo(UserInfoBean result,
                              TokenBean tokenBean,
                              String userAccount,
                              String userpassword,
                              List<BoardItemInfoBean> list) {
        //保存前先清空内容
        SPUtils.clear(getApplicationContext());

        //构造两个StringBuilder对象， 拼接用逗号分隔， 写入SharedPreferences
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            boardTitle.append(list.get(i).getTitle());
            boardId.append(list.get(i).getBoard_id());

            if (i != list.size() - 1) {
                boardTitle.append(Constant.SEPARATECOMMA);
                boardId.append(Constant.SEPARATECOMMA);
            }
        }

        new SPBuild(getApplicationContext())
                .addData(Constant.ISLOGIN, Boolean.TRUE)
                .addData(Constant.LOGINTIME, System.currentTimeMillis())//登陆时间
                .addData(Constant.USERACCOUNT, userAccount)//账号
                .addData(Constant.USERPASSWORD, userpassword)//密码
                .addData(Constant.TOKENACCESS, tokenBean.getAccess_token())
                .addData(Constant.TOKENREFRESH, tokenBean.getRefresh_token())
                .addData(Constant.TOKENTYPE, tokenBean.getToken_type())
                .addData(Constant.TOKENEXPIRESIN, tokenBean.getExpires_in())
                .addData(Constant.USERNAME, result.getUsername())
                .addData(Constant.USERID, String.valueOf(result.getUser_id()))
                .addData(Constant.USERHEADKEY, result.getAvatar().getKey())
                .addData(Constant.USEREMAIL, result.getEmail())
                .addData(Constant.BOARDTITLEARRAY, boardTitle.toString())
                .addData(Constant.BOARDIDARRAY, boardId.toString())
                .build();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mProgressLogin.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mProgressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

    }

}