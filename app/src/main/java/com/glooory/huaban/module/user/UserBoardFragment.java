package com.glooory.huaban.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.UserBoardAdapter;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/3 0003 18:18.
 */
public class UserBoardFragment extends BaseUserFragment{
    private UserBoardAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_user_recyclerview, container, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        firstHttpRequest();
    }

    private void initAdapter() {
        mAdapter = new UserBoardAdapter(mContext, isMe);


        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
        mAdapter.setLoadingView(loadMoreView);

        //当当前position等于PAGE_SIZE 时，就回调用onLoadMoreRequested() 自动加载下一页数据
        mAdapter.openLoadMore(PAGESIZE);
        // 设置空数据时的View
        TextView emptyTv = new TextView(mContext);
        emptyTv.setText(getString(R.string.empty_data));
        mAdapter.setEmptyView(emptyTv);

        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.framelayout_image:
                        // TODO: 2016/9/4 0004 launch ImageDetailActivity
                        Toast.makeText(getContext(), "launch ImageDetailActivity", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.linearlayout_group:
                        //// TODO: 2016/9/4 0004 board edit action
                        Toast.makeText(getContext(), "edit action", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void firstHttpRequest() {

        Subscription subscription = RetrofitClient.createService(UserApi.class)
                .httpUserBoardService(mAuthorization, userId, Constant.LIMIT)
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(new Func1<List<UserBoardItemBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserBoardItemBean> userBoardItemBeen) {
                        return userBoardItemBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        setMaxId(userBoardItemBeen);
                        mAdapter.setNewData(userBoardItemBeen);
                        if (mContext instanceof UserActivity) {
                            ((UserActivity) mContext).mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

    private void moreHttpRequest(int max) {

        Subscription subscription = new RetrofitClient().createService(UserApi.class)
                .httpUserBoardMaxService(mAuthorization, userId, max, Constant.LIMIT)
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(new Func1<List<UserBoardItemBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserBoardItemBean> userBoardItemBeen) {
                        return userBoardItemBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("LoadMore onCompeted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        setMaxId(userBoardItemBeen);
                        mAdapter.addData(userBoardItemBeen);
                    }
                });
    }

    private void setMaxId(List<UserBoardItemBean> beans) {
        mMaxId = beans.get(beans.size() - 1).getBoard_id();
    }

    @Override
    public void onLoadMoreRequested() {
        moreHttpRequest(mMaxId);
    }
}
