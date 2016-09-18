package com.glooory.huaban.module.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.SearchApi;
import com.glooory.huaban.base.BaseResultFragment;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/18 0018 13:30.
 */
public class ResultPinFragment extends BaseResultFragment {
    private PinQuickAdapter mAdapter;

    public static ResultPinFragment newInstance(String authorization, String keyWord) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putString(Constant.SEARCH_KEY_WORD, keyWord);
        ResultPinFragment fragment = new ResultPinFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initAdapter() {

        mAdapter = new PinQuickAdapter(mContext);
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
                    case R.id.item_card_pin_img_ll:
                        float ratio = mAdapter.getItem(i).getFile().getWidth()
                                / ((float) mAdapter.getItem(i).getFile().getHeight());
                        ImageDetailActivity.launch(getActivity(),
                                mAdapter.getItem(i).getPin_id(),
                                ratio,
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_img));
                        break;
                    case R.id.item_card_via_ll:
                        UserActivity.launch(getActivity(), String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUser().getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_avaterimg));
                        break;
                }
            }
        });

    }

    @Override
    public PinQuickAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void httpForFirstTime() {

        RetrofitClient.createService(SearchApi.class)
                .httpSearchPinsService(mAuthorization, mKeyWord, mPageCount, Constant.LIMIT)
                .map(new Func1<PinsListBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(PinsListBean pinsListBean) {
                        Logger.d(pinsListBean.getPins() == null);
                        return pinsListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinsBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsBean> list) {
                        Logger.d(list == null);
                        return list.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsBean>>() {
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
                    public void onNext(List<PinsBean> list) {
                        mPageCount = ++mPageCount;
                        mDataCountLastRequested = list.size();
                        mAdapter.setNewData(list);
                    }
                });

    }

    @Override
    public void httpForMoreData() {

        RetrofitClient.createService(SearchApi.class)
                .httpSearchPinsService(mAuthorization, mKeyWord, mPageCount, Constant.LIMIT)
                .map(new Func1<PinsListBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(PinsListBean pinsListBean) {
                        return pinsListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinsBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinsBean> list) {
                        return list.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsBean>>() {
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
                        checkIfAddFooter();
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        mPageCount = ++mPageCount;
                        mDataCountLastRequested = list.size();
                        mAdapter.addData(list);
                    }
                });

    }
}
