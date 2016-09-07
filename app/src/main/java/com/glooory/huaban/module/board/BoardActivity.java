package com.glooory.huaban.module.board;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.orhanobut.logger.Logger;

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

    public static void launch(Activity activity, UserBoardItemBean boardItemBean, String userName) {
        Intent intent = new Intent(activity, BoardActivity.class);
        intent.putExtra(Constant.BOARD_ITEM_BEAN, boardItemBean);
        intent.putExtra(Constant.USERNAME, userName);
        activity.startActivity(intent);
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
        // TODO: add setContentView(...) invocation
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
        mCollapsingtoolbar.setTitle(mBoardBean.getTitle());

        Logger.d(mUserName == null);
        mTvBoardUserName.setText(mUserName);

        if (!TextUtils.isEmpty(mBoardBean.getDescription()) && mBoardBean.getDescription() != null
                && !mBoardBean.getDescription().equals(" ")) {
            mTvBoardDes.setText(mBoardBean.getDescription());
        } else {
            mTvBoardDes.setVisibility(View.INVISIBLE);
        }

//        new RetrofitClient().createService(BoardApi.class)
//                .httpForBoardInfoService(mAuthorization, mBoardBean.getBoard_id())
//                .map(new Func1<SimpleBoardInfoBean, SimpleBoardInfoBean.BoardBean>() {
//                    @Override
//                    public SimpleBoardInfoBean.BoardBean call(SimpleBoardInfoBean simpleBoardInfoBean) {
//                        return simpleBoardInfoBean.getBoard();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<SimpleBoardInfoBean.BoardBean>() {
//                    @Override
//                    public void onCompleted() {
//                        mTvBoardUserName.setVisibility(View.INVISIBLE);
//                        Logger.d("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(e.getMessage());
//                        mTvBoardUserName.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onNext(SimpleBoardInfoBean.BoardBean boardBean) {
//                        Logger.d(boardBean.getUser().getUsername());
//                        mTvBoardUserName.setText("来自 " + boardBean.getUser().getUsername());
//                    }
//                });

        String coverUrl = "";
        if (mBoardBean.getPins().size() > 0) {
            String coverKey = mBoardBean.getPins().get(0).getFile().getKey();
            if (!TextUtils.isEmpty(coverKey)) {
                coverUrl = getString(R.string.urlImageRoot) + coverKey + getString(R.string.image_suffix_small);
            }
        }
        if (!TextUtils.isEmpty(coverUrl)) {
            new FrescoLoader.Builder(mContext, mImgBoardCover, coverUrl)
                    .setIsRadius(false)
                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
                        @Override
                        protected void onNewResultImpl(Bitmap bitmap) {
                            if (bitmap != null) {
                                Bitmap low = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
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
            return BoardPinsFragment.newInstance(mAuthorization, mBoardBean.getBoard_id(), mBoardBean.getPin_count());
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "52 采集";
        }

    }

}
