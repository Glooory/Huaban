package com.glooory.huaban.module.board;

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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.glooory.huaban.R;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.module.user.UserBoardItemBean;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.FastBlurUtil;
import com.glooory.huaban.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/7 0007 9:29.
 */
public class BoardActivity extends BaseActivity {

    @BindView(R.id.relative_board_board_info)
    RelativeLayout mRlBoardInfo;
    @BindView(R.id.coordinator_board)
    CoordinatorLayout coordinatorBoard;
    private UserBoardItemBean mBoardBean;
    private BoardSectionAdapter mAdapter;
    private String mUserName;

    @BindView(R.id.tv_board_board_des)
    TextView mTvBoardDes;
    @BindView(R.id.img_board_board_cover)
    SimpleDraweeView mImgBoardCover;
    @BindView(R.id.tv_board_board_user)
    TextView mTvBoardUserName;
    @BindView(R.id.btn_board_top_operation)
    Button mBtnTopOperation;
    @BindView(R.id.toolbar_board)
    Toolbar mToolbar;
    @BindView(R.id.collapsingtoolbar_board)
    CollapsingToolbarLayout mCollapsingtoolbar;
    @BindView(R.id.tablayout_board)
    TabLayout mTablayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.container_viewpager)
    ViewPager mViewpager;

    public static void launch(Activity activity, UserBoardItemBean boardItemBean, String userName, SimpleDraweeView animView) {
        Intent intent = new Intent(activity, BoardActivity.class);
        intent.putExtra(Constant.BOARD_ITEM_BEAN, boardItemBean);
        intent.putExtra(Constant.USERNAME, userName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animView.setTransitionName(activity.getString(R.string.board_tran));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, animView, activity.getString(R.string.board_tran)
            ).toBundle());
        } else {
            activity.startActivity(intent);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_board;
    }

    @Override
    protected String getTAG() {
        return "BoardActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mBoardBean = getIntent().getParcelableExtra(Constant.BOARD_ITEM_BEAN);
        mUserName = getIntent().getExtras().getString(Constant.USERNAME);
        initView();
        setUpViews();
    }

    private void initView() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAdapter = new BoardSectionAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mViewpager.setOffscreenPageLimit(2);

        mTablayout.setupWithViewPager(mViewpager);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float c = -verticalOffset;
                float t = mAppBar.getTotalScrollRange();
                float a = (float) (1.0 - c / (t / 2));
                mRlBoardInfo.setAlpha(a);
            }
        });

    }

    private void setUpViews() {

        if (mBoardBean == null) {
            return;
        }

        if (mBoardBean.isFollowing()) {
            mBtnTopOperation.setText(R.string.follow_action_unfollow);
        } else {
            mBtnTopOperation.setText(R.string.follow_action_follow);
        }

        mCollapsingtoolbar.setExpandedTitleColor(Color.WHITE);
        getSupportActionBar().setTitle(mBoardBean.getTitle());

        mTvBoardUserName.setText(setUpUserSpan(mUserName));

        if (!TextUtils.isEmpty(mBoardBean.getDescription()) && mBoardBean.getDescription() != null
                && !mBoardBean.getDescription().equals(" ")) {
            mTvBoardDes.setText(mBoardBean.getDescription());
        } else {
            mTvBoardDes.setVisibility(View.INVISIBLE);
        }

        String coverUrl = "";
        if (mBoardBean.getPins().size() > 0) {
            String coverKey = mBoardBean.getPins().get(0).getFile().getKey();
            if (!TextUtils.isEmpty(coverKey)) {
                coverUrl = getString(R.string.urlImageRoot) + coverKey + getString(R.string.image_suffix_small);
            }
        }
        mImgBoardCover.setAspectRatio(1.0f);
        if (!TextUtils.isEmpty(coverUrl)) {
            mImgBoardCover.setVisibility(View.VISIBLE);
            new FrescoLoader.Builder(mContext, mImgBoardCover, coverUrl)
                    .setIsRadius(false)
                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                        @Override
                        protected void onNewResultImpl(Bitmap bitmap) {
                            if (bitmap != null) {
                                Bitmap low = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                                final Drawable blured = new BitmapDrawable(getResources(), FastBlurUtil.doBlur(low, 10, false));
                                if (Utils.checkUIThreadBoolean()) {
                                    mAppBar.setBackground(blured);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAppBar.setBackground(blured);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                        }
                    })
                    .build();
        }

    }

    private class BoardSectionAdapter extends FragmentPagerAdapter {

        public BoardSectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return BoardPinsFragment.newInstance(mAuthorization,
                            mBoardBean.getBoard_id(),
                            mBoardBean.getPin_count());
                case 1:
                    return BoardFollowerFragment.newInstance(mAuthorization,
                            mBoardBean.getBoard_id(),
                            mBoardBean.getFollow_count());
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return String.valueOf(mBoardBean.getPin_count()) + " 采集";
                case 1:
                    return "被 " + mBoardBean.getFollow_count() + " 人关注";
            }
            return "";
        }

    }

    private SpannableString setUpUserSpan(String username) {
        SpannableString ss = new SpannableString("来自 " + username);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.grey_400)),
                0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(0.9f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

}
