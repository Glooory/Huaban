package com.glooory.huaban.module.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.ResultUserAdapter;
import com.glooory.huaban.api.SearchApi;
import com.glooory.huaban.base.BaseResultFragment;
import com.glooory.huaban.entity.PinsUserBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/18 0018 18:52.
 */
public class ResultUserFragment extends BaseResultFragment {
    private ResultUserAdapter mAdapter;

    public static ResultUserFragment newInstance(String authorization, String keyWord) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putString(Constant.SEARCH_KEY_WORD, keyWord);
        ResultUserFragment fragment = new ResultUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initAdapter() {

        mAdapter = new ResultUserAdapter(mContext);
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
                    case R.id.card_result_user:
                        UserActivity.launch(getActivity(), String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.img_card_result_user_avatar));
                        break;
                }
            }
        });

    }

    @Override
    public ResultUserAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void httpForFirstTime() {
        Subscription s = RetrofitClient.createService(SearchApi.class)
                .httpSearchUsersService(mAuthorization, mKeyWord, mPageCount, Constant.LIMIT)
                .map(new Func1<ResultUserListBean, List<PinsUserBean>>() {
                    @Override
                    public List<PinsUserBean> call(ResultUserListBean resultUserListBean) {
                        return resultUserListBean.getUsers();
                    }
                })
                .filter(new Func1<List<PinsUserBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsUserBean> pinsUserBeen) {
                        return pinsUserBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsUserBean>>() {
                    @Override
                    public void onCompleted() {
                        checkIfAddFooter();
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<PinsUserBean> pinsUserBeen) {
                        mPageCount = ++mPageCount;
                        mDataCountLastRequested = pinsUserBeen.size();
                        mAdapter.setNewData(pinsUserBeen);
                    }
                });
        addSubscription(s);
    }

    @Override
    public void httpForMoreData() {

        Subscription s = RetrofitClient.createService(SearchApi.class)
                .httpSearchUsersService(mAuthorization, mKeyWord, mPageCount, Constant.LIMIT)
                .map(new Func1<ResultUserListBean, List<PinsUserBean>>() {
                    @Override
                    public List<PinsUserBean> call(ResultUserListBean resultUserListBean) {
                        return resultUserListBean.getUsers();
                    }
                })
                .filter(new Func1<List<PinsUserBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsUserBean> pinsUserBeen) {
                        return pinsUserBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsUserBean>>() {
                    @Override
                    public void onCompleted() {
                        checkIfAddFooter();
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<PinsUserBean> pinsUserBeen) {
                        mPageCount = ++mPageCount;
                        mDataCountLastRequested = pinsUserBeen.size();
                        mAdapter.addData(pinsUserBeen);
                    }
                });
        addSubscription(s);
    }
}
