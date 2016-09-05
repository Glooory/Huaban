package com.glooory.huaban.module.user;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.huaban.adapter.UserPinAdapter;
import com.glooory.huaban.base.BaseUserFragment;

/**
 * Created by Glooory on 2016/9/5 0005 23:20.
 */
public class UserPinFragment extends BaseUserFragment {
    private UserPinAdapter mAdapter;
    private int mPinsCount;
    private int mCurrentCount;
    private View mFooterView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserActivity) {
            if (((UserActivity) mContext).mSwipeRefreshLayout != null) {
                ((UserActivity) mContext).mSwipeRefreshLayout.setRefreshing(true);
            }
            mPinsCount = ((UserActivity) mContext).mBoardCount;
        }
    }

    @Override
    public BaseQuickAdapter getMAdapter() {
        return null;
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
