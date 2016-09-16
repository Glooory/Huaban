package com.glooory.huaban.module.type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.glooory.huaban.R;
import com.glooory.huaban.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/16 0016 22:17.
 */
public class TypeActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_TYPE = "key_fragment_type";
    public static final String KEY_TITLE_INDEX = "key_title_index";
    private String mType;
    private String[] mTabTitles = new String[]{};
    private TypePagerAdapter mAdapter;
    private int mTitleIndex;

    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    @BindView(R.id.tablayout_type)
    TabLayout mTablayout;
    @BindView(R.id.container_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.appbar_layout_type)
    AppBarLayout mAppbar;

    public static void launch(Activity activity, int position) {
        Intent intent = new Intent(activity, TypeActivity.class);
        intent.putExtra(KEY_TITLE_INDEX, position);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_type;
    }

    @Override
    protected String getTAG() {
        return "TypeActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTitleIndex = getIntent().getIntExtra(KEY_TITLE_INDEX, 0);
        mType = getResources().getStringArray(R.array.type_value)[mTitleIndex];
        mTabTitles = getResources().getStringArray(R.array.type_tabs_titles);
        initView();
    }

    private void initView() {
        String title = getResources().getStringArray(R.array.type_names)[mTitleIndex];
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        mAdapter = new TypePagerAdapter(getSupportFragmentManager());
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

    public class TypePagerAdapter extends FragmentPagerAdapter {

        public TypePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TypePinFragment.newInstance(mAuthorization, mType);
                case 1:
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

}