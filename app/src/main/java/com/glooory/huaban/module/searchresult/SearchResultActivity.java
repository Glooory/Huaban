package com.glooory.huaban.module.searchresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.glooory.huaban.R;
import com.glooory.huaban.api.SearchApi;
import com.glooory.huaban.api.callback.FragmentRefreshListener;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/18 0018 11:32.
 */
public class SearchResultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        FragmentRefreshListener {
    public static final String SEARCH_KEY_WORD = "search_key_word";
    private String mKeyWord;
    private String[] mTabTitles;
    private int mPinCount;
    private int mBoardCount;
    private int mUserCount;
    private int mPageCount = 1;
    private ResultPagerAdapter mAdapter;
    private ResultPinFragment mPinFragment;

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.coordinator_type)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    @BindView(R.id.tablayout_type)
    TabLayout mTablayout;
    @BindView(R.id.container_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.appbar_layout_type)
    AppBarLayout mAppbar;

    public static void launch(Activity activity, String searchKeyWord) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        intent.putExtra(SEARCH_KEY_WORD, searchKeyWord);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_type;
    }

    @Override
    protected String getTAG() {
        return "SearchResultActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mKeyWord = getIntent().getStringExtra(SEARCH_KEY_WORD);
        mTabTitles = new String[]{};
        setUpTabTitles();
        initView();
        httpForCountsInfo();
        saveSearchHistory();
    }

    private void initView() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mKeyWord);

        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.red_g_i), getResources().getColor(R.color.yellow_g_i),
                getResources().getColor(R.color.blue_g_i), getResources().getColor(R.color.green_g_i));
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

        mAdapter = new ResultPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int distance = -verticalOffset;
                if (distance > mAppbar.getTotalScrollRange() / 2) {
                    mToolbar.setAlpha(0f);
                } else {
                    mToolbar.setAlpha(1f);
                }

            }
        });
    }

    /**
     * 联网查询与索搜关键字匹配的采集，画板，用户数量
     * 然后更新tabtitles
     */
    private void httpForCountsInfo() {

        RetrofitClient.createService(SearchApi.class)
                .httpResultCountInfoService(mAuthorization, mKeyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultCountInfoBean>() {
                    @Override
                    public void onCompleted() {
                        setUpTabTitles();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        checkException(e, mCoordinator);
                    }

                    @Override
                    public void onNext(ResultCountInfoBean resultCountInfoBean) {
                        mPinCount = resultCountInfoBean.getPin_count();
                        mBoardCount = resultCountInfoBean.getBoard_count();
                        mUserCount = resultCountInfoBean.getPeople_count();
                    }
                });

    }

    private void setUpTabTitles() {
        String[] titles = new String[]{
                String.format(getString(R.string.format_gather_number), mPinCount),
                String.format(getString(R.string.format_board_number), mBoardCount),
                String.format(getString(R.string.format_user_number), mUserCount)
        };
        mTabTitles = titles;
    }

    /**
     * 将搜索记录保存起来
     */
    private void saveSearchHistory() {

        HashSet<String> hashSet = (HashSet<String>) SPUtils.get(getApplicationContext(), Constant.HISTORYTEXT, new HashSet<String>());
        Set<String> newData = new HashSet<>(hashSet);
        newData.add(mKeyWord);
        SPUtils.putByCommit(getApplicationContext(), Constant.HISTORYTEXT, newData);

    }

    @Override
    public void requestRefresh() {
        int position = mViewPager.getCurrentItem();
        switch (position) {
            case 0:
                if (mPinFragment != null) {
                    mPinFragment.refreshData();
                }
                break;
        }
    }

    @Override
    public void requestRefreshDone() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        httpForCountsInfo();
        requestRefresh();
    }

    public class ResultPagerAdapter extends FragmentPagerAdapter {

        public ResultPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ResultPinFragment.newInstance(mAuthorization, mKeyWord);
                case 1:
                    return new Fragment();
                case 2:
                    return new Fragment();
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    mPinFragment = (ResultPinFragment) createdFragment;
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
