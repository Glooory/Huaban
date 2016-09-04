package com.glooory.huaban.module.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.glooory.huaban.R;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.FastBlurUtil;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.util.Utils;
import com.orhanobut.logger.Logger;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/3 0003 17:46.
 */
public class UserActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public boolean isMe;
    public String mUserId;
    private String[] titles;

    @BindView(R.id.img_image_user)
    SimpleDraweeView mImgImageUser;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_location_job)
    TextView mTvUserProfile;
    @BindView(R.id.tv_user_friend)
    TextView mTvUserFriend;
    @BindView(R.id.linearlayout_user_info)
    LinearLayout mLinearlayoutUserInfo;
    @BindView(R.id.toolbar_user)
    Toolbar mToolbar;
    @BindView(R.id.collapsingtoolbar_user)
    CollapsingToolbarLayout mCollapsingtoolbar;
    @BindView(R.id.tablayout_user)
    TabLayout mTablayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.container_viewpager)
    ViewPager mViewpager;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab_opreate)
    FloatingActionButton mFabOpreate;

    @BindColor(R.color.white)
    int mColorTabIndicator;

    @BindString(R.string.url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.urlImageRoot)
    String mHttpRoot;
    @BindString(R.string.text_fans_attention)
    String mFansFollowingFormat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected String getTAG() {
        return "UserActivity";
    }

    public static void launch(Activity activity, String userId) {
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(Constant.USERID, userId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        mUserId = getIntent().getExtras().getString(Constant.USERID);
        Logger.d(mUserId);

        initRes();
        initView();
        httpForUserInfo();

    }

    private void initView() {

        setSupportActionBar(mToolbar);
        mCollapsingtoolbar.setExpandedTitleColor(Color.TRANSPARENT);//展开时文字为透明的
        mTvUserFriend.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                CompatUtils.getTintDrawable(mContext, R.drawable.ic_chevron_right_white_24dp, Color.WHITE),
                null
        );

    }

    private void initRes() {
        //判断是否是自己
        String userIdTemp = getIntent().getExtras().getString(Constant.USERID);
        String myId = (String) SPUtils.get(getApplicationContext(), Constant.USERID, "");
        isMe = myId.equals(userIdTemp);
        titles = getResources().getStringArray(R.array.user_appbar_title_array);

        SectionsPagerAdapter mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mPagerAdapter);


        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setSelectedTabIndicatorColor(mColorTabIndicator);

    }

    private void httpForUserInfo() {

        if (!TextUtils.isEmpty(mUserId)) {
            Subscription subscription = new RetrofitClient().createService(UserApi.class)
                    .httpsUserInfoRx(mAuthorization, mUserId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserBean>() {
                        @Override
                        public void onCompleted() {
                            Logger.d("onCompleted()");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.d(e.getMessage());
                        }

                        @Override
                        public void onNext(UserBean userBean) {
                            setUserTextInfo(userBean);
                            setUserImgInfo(userBean);
                        }
                    });
        }

    }

    private void setUserTextInfo(UserBean bean) {

        //设置用户名
        if (!TextUtils.isEmpty(bean.getUsername())) {
            mTvUserName.setText(bean.getUsername());
        } else {
            mTvUserName.setText(Constant.EMPTY_STRING);
        }

        //设置地址和工作
        if (bean.getProfile() != null) {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(bean.getProfile().getLocation())) {
                sb.append(bean.getProfile().getLocation())
                        .append("  ");
            }

            if (!TextUtils.isEmpty(bean.getProfile().getLocation())) {
                sb.append(bean.getProfile().getJob());
            }

            mTvUserProfile.setText(sb.toString());
        } else {
            mTvUserProfile.setText(Constant.EMPTY_STRING);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bean.getFollowing_count())
                .append(" 关注  ")
                .append(bean.getFollower_count())
                .append(" 粉丝");
        mTvUserFriend.setText(stringBuilder.toString());
        Logger.d("setUserTextInfo() executed");
    }

    /**
     * 设置模糊背景和头像
     * @param bean
     */
    private void setUserImgInfo(UserBean bean) {

        String avatarKey = bean.getAvatarBean().getKey();
        if (!TextUtils.isEmpty(avatarKey)) {
            String avatarUrl = mHttpRoot + avatarKey;

            Logger.d(avatarUrl);

            new FrescoLoader.Builder(getApplicationContext(), mImgImageUser, avatarUrl)
                    .setPlaceHolderImage(CompatUtils.getTintDrawable(mContext, R.drawable.ic_avatar_def, Color.WHITE))
                    .setIsCircle(true, true)
                    .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                        @Override
                        protected void onNewResultImpl(Bitmap bitmap) {
                            Logger.d("get bitmap");
                            if (bitmap != null) {
                                final Drawable blurDrawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(bitmap, 20, false));
                                if (Utils.checkUIThreadBoolean()) {
                                    mAppBar.setBackground(blurDrawable);
                                    Logger.d("setinUI");
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAppBar.setBackground(blurDrawable);
                                            Logger.d("setNotinUI");
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                            Logger.d(dataSource.getFailureCause().getMessage());
                        }
                    })
                    .build();
        }

        Logger.d("setUserImgInfo() finished");

    }


    @Override
    public void onRefresh() {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return UserBoardFragment.newInstance(mUserId);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[0];
        }
    }
}
