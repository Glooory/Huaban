package com.glooory.huaban.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.FollowingAdapter;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.Constant;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/15 0015 12:37.
 */
public class UserFollowingFragment extends BaseUserFragment {
    private FollowingAdapter mAdapter;

    public static UserFollowingFragment newInstance(String userId, int dataCount) {
        Bundle args = new Bundle();
        args.putString(Constant.USERID, userId);
        args.putInt(Constant.DATA_ITEM_COUNT, dataCount);
        UserFollowingFragment fragment = new UserFollowingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FollowingAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void initAdapter() {

        mAdapter = new FollowingAdapter(mContext);

        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
        mAdapter.setLoadingView(loadMoreView);

        //当当前position等于PAGE_SIZE 时，就回调用onLoadMoreRequested() 自动加载下一页数据
        mAdapter.openLoadMore(PAGESIZE);

        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.card_follower:
                        UserActivity.launch(getActivity(),
                                String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.img_card_follower_avatar));
                        break;
                }
            }
        });

    }

    @Override
    public void httpForFirstTime() {

        new RetrofitClient().createService(UserApi.class)
                .httpUserFollowingService(mAuthorization, mUserId, Constant.LIMIT)
                .map(new Func1<UserFollowingBean, List<UserFollowingBean.UsersBean>>() {
                    @Override
                    public List<UserFollowingBean.UsersBean> call(UserFollowingBean userFollowingBean) {
                        return userFollowingBean.getUsers();
                    }
                })
                .filter(new Func1<List<UserFollowingBean.UsersBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserFollowingBean.UsersBean> usersBeen) {
                        return usersBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserFollowingBean.UsersBean>>() {
                    @Override
                    public void onCompleted() {
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<UserFollowingBean.UsersBean> list) {
                        setMaxId(list);
                        mAdapter.setNewData(list);
                        mCurrentCount = mAdapter.getData().size();
                        checkIfAddFooter();
                    }
                });

    }

    @Override
    public void httpForMoreData() {

        new RetrofitClient().createService(UserApi.class)
                .httpUserFollowingMaxService(mAuthorization, mUserId, mMaxId, Constant.LIMIT)
                .map(new Func1<UserFollowingBean, List<UserFollowingBean.UsersBean>>() {
                    @Override
                    public List<UserFollowingBean.UsersBean> call(UserFollowingBean userFollowingBean) {
                        return userFollowingBean.getUsers();
                    }
                })
                .filter(new Func1<List<UserFollowingBean.UsersBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserFollowingBean.UsersBean> usersBeen) {
                        return usersBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserFollowingBean.UsersBean>>() {
                    @Override
                    public void onCompleted() {
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<UserFollowingBean.UsersBean> list) {
                        setMaxId(list);
                        mAdapter.addData(list);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });

    }

    /**
     * 保存本次网络数据请求的max值，后续联网需要
     *
     * @param list
     */
    private void setMaxId(List<UserFollowingBean.UsersBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getSeq();
                mDataCountLastRequested = list.size();
            }
        }
    }

}
