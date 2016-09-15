package com.glooory.huaban.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.util.Constant;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/6 0006 21:03.
 */
public class UserLikeFragment extends BaseUserFragment {
    private PinQuickAdapter mAdapter;
    private int mCurrentCount;



    public static UserLikeFragment newInstance(String userId, int dataCount) {
        Bundle args = new Bundle();
        args.putString(Constant.USERID, userId);
        args.putInt(Constant.DATA_ITEM_COUNT, dataCount);
        UserLikeFragment fragment = new UserLikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PinQuickAdapter getMAdapter() {
        initAdapter();
        return mAdapter;
    }

    private void initAdapter() {
        mAdapter = new PinQuickAdapter(mContext);

        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
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
                        float ratioTemp = mAdapter.getItem(i).getFile().getWidth() /
                                ((float) mAdapter.getItem(i).getFile().getHeight());
                        ImageDetailActivity.launch(getActivity(),
                                mAdapter.getItem(i).getPin_id(),
                                ratioTemp,
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_img));
                        break;
                    case R.id.item_card_via_ll:
                        UserActivity.launch(getActivity(),
                                String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUser().getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_avaterimg));
                        break;
                }
            }
        });
    }

    @Override
    public void httpForFirstTime() {

        new RetrofitClient().createService(UserApi.class)
                .httpUserLikesService(mAuthorization, mUserId, Constant.LIMIT)
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
                        checkIfAddFooter();
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        saveMaxId(list);
                        mAdapter.setNewData(list);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });


    }

    private void httpForMore() {

        new RetrofitClient().createService(UserApi.class)
                .httpUserLikesMaxService(mAuthorization, mUserId, mMaxId, Constant.LIMIT)
                .map(new Func1<PinsListBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(PinsListBean pinsListBean) {
                        return pinsListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PinsBean>>() {
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
                    public void onNext(List<PinsBean> list) {
                        saveMaxId(list);
                        mAdapter.addData(list);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });

    }

    private void saveMaxId(List<PinsBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getPin_id();
            }
        }
    }

    /**
     * 判断是否加没有更多了的FooterView
     */
    public void checkIfAddFooter() {
        if (mDateItemCount < PAGESIZE) {
            if (mFooterView.getParent() != null) {
                ((ViewGroup) mFooterView.getParent()).removeView(mFooterView);
            }
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(mFooterView);
                }
            });
        }
    }

    @Override
    public void refreshData() {
        httpForFirstTime();
    }

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCount >= mDateItemCount) {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(mFooterView);
                } else {
                    httpForMore();
                }
            }
        });

    }
}
