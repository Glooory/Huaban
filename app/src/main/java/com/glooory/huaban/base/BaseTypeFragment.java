package com.glooory.huaban.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.huaban.R;
import com.glooory.huaban.api.callback.FragmentRefreshListener;
import com.glooory.huaban.module.type.TypeActivity;
import com.glooory.huaban.util.Constant;

/**
 * Created by Glooory on 2016/9/16 0016 22:23.
 */
public abstract class BaseTypeFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    protected String mAuthorization;
    /**
     * 加载哪个分类的关键字
     */
    protected String mType;
    /**
     * 每次请求加载数据的数量，用来触发LoadMoreListener回调方法
     */
    protected final int PAGESIZE = 20;
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    protected int mMaxId;
    protected View mFooterView;
    /**
     * 上次网络请求得到的数据数量，用来判断数据是否加载完毕 如果小于PAGESIZE 则可认为加载完毕
     */
    protected int mDataCountLastRequested = 0;
    /**
     * 当前已经请求到的数据的总数， 用来判断数据是否加载完毕，如果 mCurrentCount >= mDataItemCount
     * 则判定加载完毕
     */
    protected boolean mIsLogin;
    protected FragmentRefreshListener mRefreshListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof TypeActivity) {
            mRefreshListener = (FragmentRefreshListener) context;
            mIsLogin = ((TypeActivity) context).isLogin;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (mRefreshListener != null) {
            mRefreshListener.requestRefresh();
        }
        super.onCreate(savedInstanceState);
        mAuthorization = getArguments().getString(Constant.AUTHORIZATION);
        mType = getArguments().getString(TypeActivity.KEY_FRAGMENT_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_user_recyclerview, container, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(getAdapter());
        mFooterView = inflater.inflate(R.layout.view_no_more_data_footer, null);
        httpForFirstTime();
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsLogin = ((BaseActivity) getActivity()).isLogin;
    }

    /**
     * 初始化Adpater，子类实现各自Adapter的初始化
     */
    public abstract void initAdapter();

    public abstract <T extends BaseQuickAdapter> T getAdapter();

    /**
     * 第一次联网请求数据
     */
    public abstract void httpForFirstTime();

    /**
     * 上滑自动加载更多数据
     */
    public abstract void httpForMoreData();

    /**
     * 数据加载完毕，adapter加上footerview
     */
    public void setAdapterLoadComplete() {
        if (mFooterView.getParent() != null) {
            ((ViewGroup) mFooterView.getParent()).removeView(mFooterView);
        }
        getAdapter().loadComplete();
        getAdapter().addFooterView(mFooterView);
    }

    /**
     * 判断当前数据是否已经加载完毕和加没有更多了的FooterView
     */
    public void checkIfAddFooter() {
        if (mDataCountLastRequested < PAGESIZE) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    setAdapterLoadComplete();
                }
            });
        }
    }

    /**
     * Adapter上滑自动加载更多数据的回调
     */
    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mDataCountLastRequested < PAGESIZE) {
                    setAdapterLoadComplete();
                } else {
                    httpForMoreData();
                }
            }
        });

    }

    /**
     * 刷新当前子类的数据，提供给Activity调用
     */
    public void refreshData(){
        httpForFirstTime();
    }

}
