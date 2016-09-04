package com.glooory.huaban.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.huaban.R;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.module.user.UserBoardFragment;
import com.glooory.huaban.util.Constant;

/**
 * Created by Glooory on 2016/9/4 0004 11:23.
 */
public abstract class BaseUserFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    protected boolean isMe;
    protected String mAuthorization;
    protected int userItemCount; // 当前用户的画板获取采集总数，用来判断显示已经没有更多内容的footer view
    protected final int PAGESIZE = 20;
    protected String userId;
    protected RecyclerView mRecyclerView;
    protected Context mContext;
    protected int mMaxId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof UserActivity) {
            mAuthorization = ((UserActivity) context).mAuthorization;
            isMe = ((UserActivity) context).isMe;
        }
    }

    public static UserBoardFragment newInstance(String userId) {

        Bundle args = new Bundle();
        args.putString(Constant.USERID, userId);
        UserBoardFragment fragment = new UserBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString(Constant.USERID);
    }

}
