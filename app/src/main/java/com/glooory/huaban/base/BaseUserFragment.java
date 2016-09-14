package com.glooory.huaban.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.huaban.R;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;

/**
 * Created by Glooory on 2016/9/4 0004 11:23.
 */
public abstract class BaseUserFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    protected boolean isMe;
    protected String mAuthorization;
    protected final int PAGESIZE = 20;
    protected String mUserId;
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    protected int mMaxId;
    protected View mFooterView;
    protected int mDateItemCount;
    protected FragmentRefreshListener mRefreshListener;
    protected boolean mIsLogin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof UserActivity) {
            mAuthorization = ((UserActivity) context).mAuthorization;
            isMe = ((UserActivity) context).isMe;
            mIsLogin = ((UserActivity) context).isLogin;
            mRefreshListener = ((UserActivity) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (mRefreshListener != null) {
            mRefreshListener.requestRefresh();
        }
        super.onCreate(savedInstanceState);
        mUserId = getArguments().getString(Constant.USERID);
        mDateItemCount = getArguments().getInt(Constant.DATA_ITEM_COUNT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_user_recyclerview, container, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(getMAdapter());
        mFooterView = inflater.inflate(R.layout.view_no_more_data_footer, container, false);
        httpForFirstTime();
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsLogin = ((UserActivity) getActivity()).isLogin;
    }

    public void setDateItemCount(int dateItemCount) {
        this.mDateItemCount = dateItemCount;
    }

    public abstract <T extends BaseQuickAdapter> T getMAdapter();

    public abstract void httpForFirstTime();

    public abstract void refreshData();

    public interface FragmentRefreshListener {

        void requestRefresh();

        void requestRefreshDone();

    }
}
