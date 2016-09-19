package com.glooory.huaban.module.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.login.LoginActivity;
import com.glooory.huaban.module.login.UserInfoBean;
import com.glooory.huaban.module.search.SearchActivity;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.container_main)
    FrameLayout containerMain;
    @BindView(R.id.coordinator_main)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.appbar_layout_main)
    AppBarLayout mAppbar;

    private LinearLayout mDrawerAvatarLL;
    private long exitTime = 0;
    //侧滑菜单头像
    private SimpleDraweeView mAvatarImg;
    //侧滑菜单用户名
    private TextView mUserNameTv;
    //侧滑菜单采集数
    private TextView mCollectionTv;
    //侧滑菜单画板数量
    private TextView mBoardCountTv;
    //侧滑菜单关注数量
    private TextView mFollowingTv;
    //侧滑菜单粉丝数
    private TextView mFansCountTv;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTAG() {
        return "MainActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        if (isLogin) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_main, PinsFragment.newInstance(mAuthorization, PinsFragment.HOME_FRAGMENT_INDEX))
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_main, PinsFragment.newInstance(mAuthorization, PinsFragment.NEWEST_FRAGMENT_INDEX))
                    .commit();
        }
    }

    @Override
    protected void initResAndListener() {
        setSupportActionBar(mToolbar);
        if (isLogin) {
            getSupportActionBar().setTitle(R.string.nav_homepage);
        } else {
            getSupportActionBar().setTitle(R.string.nav_newest);
        }
        RxView.clicks(mFab)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)  //防抖动处理
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(mContext, "replace your own action with this", Toast.LENGTH_SHORT).show();
                    }
                });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        initDrawerHeader();
        initDrawerMenu();

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float off = -verticalOffset;
                if (off > appBarLayout.getTotalScrollRange() / 2) {
                    mToolbar.setAlpha(0.0f);
                } else {
                    //当Collapsingtoolbar 滑动到最顶端时，隐藏从通明状态栏能看得见的View，
                    // 如果不隐藏，从透明状态栏能看见部分toolbar，影响用户体验
                    mToolbar.setAlpha(1.0f);
                }
            }
        });
    }

    //手动填充DrawerLayout 的headerView
    private void initDrawerHeader() {

        View headerView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mDrawerAvatarLL = (LinearLayout) headerView.findViewById(R.id.drawer_avatar_ll);
        mDrawerAvatarLL.setOnClickListener(this);

        mAvatarImg = (SimpleDraweeView) headerView.findViewById(R.id.drawer_avatar_img);
        mAvatarImg.setImageResource(R.drawable.ic_avatar_def);
        mUserNameTv = (TextView) headerView.findViewById(R.id.tv_drawer_username);
        mCollectionTv = (TextView) headerView.findViewById(R.id.tv_drawer_collection);
        mBoardCountTv = (TextView) headerView.findViewById(R.id.tv_drawer_board);
        mFollowingTv = (TextView) headerView.findViewById(R.id.tv_drawer_following);
        mFansCountTv = (TextView) headerView.findViewById(R.id.tv_drawer_fans);

    }

    private void initDrawerMenu() {
        Menu memu = mNavView.getMenu();
        memu.getItem(isLogin ? 0 : 1).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogin) {
            setNavUserInfo();
            final String userId = (String) SPUtils.get(getApplication(), Constant.USERID, "");
            Logger.d(userId);
            Subscription s = RetrofitClient.createService(UserApi.class)
                    .httpsUserInfoRx(mAuthorization, userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfoBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(UserInfoBean userBean) {
                            mCollectionTv.setText(String.valueOf(userBean.getPin_count()));
                            mBoardCountTv.setText(String.valueOf(userBean.getBoard_count()));
                            mFollowingTv.setText(String.valueOf(userBean.getFollowing_count()));
                            mFansCountTv.setText(String.valueOf(userBean.getFollower_count()));
                        }
                    });
            addSubscription(s);
        }
    }

    private void setNavUserInfo() {
        String userName = (String) SPUtils.get(getApplicationContext(), Constant.USERNAME, "");
        String userAvatarKey = (String) SPUtils.get(getApplicationContext(), Constant.USERHEADKEY, "");

        if (!TextUtils.isEmpty(userAvatarKey)) {
            String userAvatarUrl = getString(R.string.urlImageRoot) + userAvatarKey;
            new FrescoLoader.Builder(this, mAvatarImg, userAvatarUrl)
                    .setIsCircle(false)
                    .build();
        } else {
            Logger.d("user avatar key is empty");
        }

        if (!TextUtils.isEmpty(userName)) {
            mUserNameTv.setText(userName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                SearchActivity.launch(MainActivity.this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_homepage) {
            if (isLogin) {
                Logger.d(mAuthorization);
                mFragmentManager.beginTransaction()
                        .replace(R.id.container_main, PinsFragment.newInstance(mAuthorization, PinsFragment.HOME_FRAGMENT_INDEX))
                        .commit();
                getSupportActionBar().setTitle(R.string.nav_homepage);
            } else {
                showLoginSnackbar(MainActivity.this, mCoordinator);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        } else if (id == R.id.nav_newest) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_main, PinsFragment.newInstance(mAuthorization, PinsFragment.NEWEST_FRAGMENT_INDEX))
                    .commit();
            getSupportActionBar().setTitle(R.string.nav_newest);
        } else if (id == R.id.nav_popular) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.container_main, PinsFragment.newInstance(mAuthorization, PinsFragment.POPULAR_FRAGMENT_INDEX))
                    .commit();
            getSupportActionBar().setTitle(R.string.nav_popular);
        } else if (id == R.id.nav_discover) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            SearchActivity.launch(MainActivity.this);
            return false;
        } else if (id == R.id.nav_exit) {
            if (isLogin) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_delete_attention_title)
                        .setMessage(R.string.dialog_exit_account_attention)
                        .setPositiveButton(R.string.dialog_edit_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SPUtils.clear(getApplicationContext());
                                LoginActivity.launch(MainActivity.this);
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_negative, null);
                builder.create().show();
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_avatar_ll:
                if (isLogin) {
                    UserActivity.launch(MainActivity.this,
                            (String) SPUtils.get(getApplicationContext(), Constant.USERID, ""),
                            (String) SPUtils.get(getApplicationContext(), Constant.USERNAME, ""),
                            mAvatarImg);
                } else {
                    LoginActivity.launch(this);
                }
                break;
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Toast.makeText(MainActivity.this, R.string.exit_hint, Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finishSelf();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
