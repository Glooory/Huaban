package com.glooory.huaban.module.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private LinearLayout mDrawerAvatarLL;
    private LinearLayout mDrawerCollection;
    private LinearLayout mDrawerBoard;
    private LinearLayout mDrawerFans;
    private long exitTime;
    //侧滑菜单头像
    private SimpleDraweeView mAvatarImg;
    //侧滑菜单用户名
    private TextView mUserNameTv;
    //侧滑菜单采集数
    private TextView mCollectionTv;
    //侧滑菜单画板数量
    private TextView mBoardCountTv;
    //侧滑菜单粉丝数
    private TextView mFansCountTv;

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_main, new PinsFragment()).commit();
    }

    @Override
    protected void initResAndListener() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(mNavView.getMenu().getItem(0).getTitle());
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
    }

    //手动填充DrawerLayout 的headerView
    private void initDrawerHeader() {

        View headerView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mDrawerAvatarLL = (LinearLayout) headerView.findViewById(R.id.drawer_avatar_ll);
        mDrawerCollection = (LinearLayout) headerView.findViewById(R.id.drawer_collecion);
        mDrawerBoard = (LinearLayout) headerView.findViewById(R.id.drawer_board);
        mDrawerFans = (LinearLayout) headerView.findViewById(R.id.drawer_fans);
        mDrawerAvatarLL.setOnClickListener(this);
        mDrawerCollection.setOnClickListener(this);
        mDrawerBoard.setOnClickListener(this);
        mDrawerFans.setOnClickListener(this);

        mAvatarImg = (SimpleDraweeView) headerView.findViewById(R.id.drawer_avatar_img);
        mAvatarImg.setImageResource(R.drawable.ic_avatar_def);
        mUserNameTv = (TextView) headerView.findViewById(R.id.tv_drawer_username);
        mCollectionTv = (TextView) headerView.findViewById(R.id.tv_drawer_collection);
        mBoardCountTv = (TextView) headerView.findViewById(R.id.tv_drawer_board);
        mFansCountTv = (TextView) headerView.findViewById(R.id.tv_drawer_fans);

    }

    private void initDrawerMenu() {
        Menu memu = mNavView.getMenu();
        memu.getItem(0).setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogin) {
            setNavUserInfo();
            final String userId = (String) SPUtils.get(getApplication(), Constant.USERID, "");
            Logger.d(userId);
            RetrofitClient.createService(UserApi.class)
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
                            mFansCountTv.setText(String.valueOf(userBean.getFollower_count()));
                        }
                    });

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
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            // Handle the camera action
        } else if (id == R.id.nav_newest) {

        } else if (id == R.id.nav_discover) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_avatar_ll:
                // TODO: 2016/8/31 0031 lanuch UserActivity
                if (isLogin) {
                    UserActivity.launch(MainActivity.this,
                            (String) SPUtils.get(getApplicationContext(), Constant.USERID, ""),
                            (String) SPUtils.get(getApplicationContext(), Constant.USERNAME, ""),
                            mAvatarImg);
                } else {
                    LoginActivity.launch(this);
                }
                break;
            case R.id.drawer_collecion:
                // TODO: 2016/8/31 0031 Launch UserActvity
                Toast.makeText(this, "Launch UserActvity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_board:
                // TODO: 2016/8/31 0031 Launch BoardActvity
                Toast.makeText(this, "Launch BoardActvity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_fans:
                // TODO: 2016/8/31 0031 lanuch UserActivity
                Toast.makeText(this, "Launch Activity", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void headerClick(View view) {
        // TODO: 2016/8/31 0031 Launch UserActivity
        Toast.makeText(this, "Launch UserActivity", Toast.LENGTH_SHORT).show();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


}
