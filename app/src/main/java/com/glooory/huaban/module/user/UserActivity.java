package com.glooory.huaban.module.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.glooory.huaban.R;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.login.LoginActivity;
import com.glooory.huaban.module.login.UserInfoBean;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/3 0003 17:46.
 */
public class UserActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseUserFragment.FragmentRefreshListener {
    public boolean isMe;
    private String mUserId;
    @BindView(R.id.btn_follow_operation)
    Button mBtnFollowOperation;
    @BindView(R.id.coordinator_user)
    CoordinatorLayout mCoordinator;
    private String[] titles;
    public int mBoardCount;
    public int mCollectionCount;
    public int mLikeCount;
    private SectionsPagerAdapter mPagerAdapter;
    private boolean isFollowing;
    private int mCurrentPosition = 0;
    private UserBoardFragment boardFragment;
    private UserPinFragment pinFragment;
    private UserLikeFragment likeFragment;
    private String mUserName;

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

    @BindColor(R.color.colorPrimary)
    int mColorTabIndicator;

    @BindString(R.string.format_url_image_small)
    String mFormatUrlSmall;
    @BindString(R.string.urlImageRoot)
    String mHttpRoot;
    @BindString(R.string.format_fans_follow)
    String mFansFollowingFormat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected String getTAG() {
        return "UserActivity";
    }

    public static void launch(Activity activity, String userId, String userName, SimpleDraweeView tranView) {
        Intent intent = new Intent(activity, UserActivity.class);
        intent.putExtra(Constant.USERID, userId);
        intent.putExtra(Constant.USERNAME, userName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tranView.setTransitionName(activity.getResources().getString(R.string.avatar_tran));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, tranView, activity.getResources().getString(R.string.avatar_tran)
            ).toBundle());
        } else {
            activity.startActivity(intent);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mUserId = getIntent().getExtras().getString(Constant.USERID);

        initRes();
        initView();
        httpForUserInfo();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER));
//            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.CENTER_CROP));
//        }

    }

    private void initView() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBtnFollowOperation.setVisibility(View.GONE);//最初先隐藏，后面再根据情况显示
        setUpToolBtn();
        topActionBtnSetOnListener();

        mCollapsingtoolbar.setExpandedTitleColor(Color.TRANSPARENT);//展开时文字为透明的
        mTvUserFriend.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                CompatUtils.getTintDrawable(mContext, R.drawable.ic_chevron_right_white_24dp, Color.WHITE),
                null
        );

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float off = -verticalOffset;
                float alpha = 1.0f - off / (appBarLayout.getTotalScrollRange() / 2.0f);
                mLinearlayoutUserInfo.setAlpha(alpha);
            }
        });

        mTablayout.setSelectedTabIndicatorColor(mColorTabIndicator);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setUpToolBtn();
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTablayout.setupWithViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(3);
        mViewpager.setCurrentItem(mCurrentPosition, true);

    }

    private void initRes() {
        //判断是否是自己
        String userIdTemp = getIntent().getExtras().getString(Constant.USERID);
        mUserName = getIntent().getExtras().getString(Constant.USERNAME);
        String myId = (String) SPUtils.get(getApplicationContext(), Constant.USERID, "");
        isMe = myId.equals(userIdTemp);
        titles = getResources().getStringArray(R.array.user_appbar_title_array);
    }

    private void httpForUserInfo() {

        mSwipeRefreshLayout.setRefreshing(true);

        if (!TextUtils.isEmpty(mUserId)) {
            new RetrofitClient().createService(UserApi.class)
                    .httpsUserInfoRx(mAuthorization, mUserId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserInfoBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.d(e.getMessage());
                        }

                        @Override
                        public void onNext(UserInfoBean userBean) {
                            saveItemsCount(userBean);
                            setUserTextInfo(userBean);
                            setUserImgInfo(userBean);
                            setUserIsFollowing(userBean);
                        }
                    });
        }

    }

    private void saveItemsCount(UserInfoBean bean) {
        mBoardCount = bean.getBoard_count();
        mCollectionCount = bean.getPin_count();
        mLikeCount = bean.getLike_count();
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mPagerAdapter);
        String title1 = String.format(getString(R.string.format_board_number), bean.getBoard_count());
        String title2 = String.format(getString(R.string.format_gather_number), bean.getPin_count());
        String title3 = String.format(getString(R.string.format_like_number), bean.getLike_count());
        titles = new String[]{title1, title2, title3};
        mViewpager.getAdapter().notifyDataSetChanged();
    }

    private void setUserTextInfo(UserInfoBean bean) {

        //设置用户名
        if (!TextUtils.isEmpty(bean.getUsername())) {
            mTvUserName.setText(bean.getUsername());
            mCollapsingtoolbar.setTitle(bean.getUsername());

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
            if (bean.getProfile().getJob() != null) {
                if (!TextUtils.isEmpty(bean.getProfile().getLocation())) {
                    sb.append(bean.getProfile().getJob());
                }
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

    }

    /**
     * 设置模糊背景和头像
     *
     * @param bean
     */
    private void setUserImgInfo(UserInfoBean bean) {
        String avatarUrl = String.format(getString(R.string.format_url_image_small), bean.getAvatar().getKey());
        if (!TextUtils.isEmpty(avatarUrl)) {

            mImgImageUser.setVisibility(View.VISIBLE);
            new FrescoLoader.Builder(mContext, mImgImageUser, avatarUrl)
                    .setIsCircle(true, true)
                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                        @Override
                        protected void onNewResultImpl(Bitmap bitmap) {
                            if (bitmap != null) {
                                final Bitmap low = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                                final Drawable blurDrawable = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(low, 10, false));
                                if (Utils.checkUIThreadBoolean()) {
                                    mAppBar.setBackground(blurDrawable);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAppBar.setBackground(blurDrawable);
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

    }

    private void setUserIsFollowing(UserInfoBean bean) {

        isFollowing = bean.getFollowing() == 1;
        setUpToolBtn();

    }

    @Override
    public void onRefresh() {

        httpForUserInfo();

        requestFragmentRefresh();
    }

    //根据登录状态和是否是自己的用户信息来决定toolbar 上面的button的文字
    private void setUpToolBtn() {

        if (isMe) {
            switch (mTablayout.getSelectedTabPosition()) {
                case 0:
                    mBtnFollowOperation.setText(R.string.create_new_board);
                    mBtnFollowOperation.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mBtnFollowOperation.setText(R.string.add_new_collection);
                    mBtnFollowOperation.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mBtnFollowOperation.setText("");
                    mBtnFollowOperation.setVisibility(View.GONE);
                    break;
            }
        } else {
            if (isLogin) {
                if (isFollowing) {
                    mBtnFollowOperation.setText(R.string.follow_action_unfollow);
                    mBtnFollowOperation.setVisibility(View.VISIBLE);
                } else {
                    mBtnFollowOperation.setText(R.string.follow_action_follow);
                    mBtnFollowOperation.setVisibility(View.VISIBLE);
                }
            } else {
                mBtnFollowOperation.setText(R.string.follow_action_follow);
                mBtnFollowOperation.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * toolbar 右上角的Button 的点击事件
     */
    private void topActionBtnSetOnListener() {

        mBtnFollowOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMe) {
                    switch (mTablayout.getSelectedTabPosition()) {
                        case 0:
                            // TODO: 2016/9/6 0006 create new board operate
                            break;
                        case 1:
                            // TODO: 2016/9/6 0006 add a new pin operate
                            break;
                    }
                } else {
                    if (isLogin) {
                        if (isFollowing) {
                            // TODO: 2016/9/6 0006 unfollow operate
                        } else {
                            // TODO: 2016/9/6 0006 follow operate
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(mCoordinator, R.string.need_login, Snackbar.LENGTH_LONG);
                        snackbar.setAction(R.string.go_to_login, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LoginActivity.launch(UserActivity.this);
                            }
                        }).show();
                    }
                }
            }
        });

    }

    private void requestFragmentRefresh() {

        int currentIndex = mViewpager.getCurrentItem();
        switch (currentIndex) {
            case 0:
                if (boardFragment != null) {
                    boardFragment.refreshData();
                }
                break;
            case 1:
                if (pinFragment != null) {
                    pinFragment.refreshData();
                }
                break;
            case 2:
                if (likeFragment != null) {
                    likeFragment.refreshData();
                }
                break;
        }

    }

    @Override
    public void requestRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void requestRefreshDone() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return UserBoardFragment.newInstance(mUserId, mBoardCount, mUserName);
                case 1:
                    return UserPinFragment.newInstance(mUserId, mCollectionCount);
                case 2:
                    return UserLikeFragment.newInstance(mUserId, mLikeCount);
                default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    boardFragment = (UserBoardFragment) createdFragment;
                    break;
                case 1:
                    pinFragment = (UserPinFragment) createdFragment;
                    break;
                case 2:
                    likeFragment = (UserLikeFragment) createdFragment;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
