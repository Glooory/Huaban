package com.glooory.huaban.module.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.UserPinAdapter;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/5 0005 23:20.
 */
public class UserPinFragment extends BaseUserFragment {
    private UserPinAdapter mAdapter;

    public static UserPinFragment newInstance(String userId, int dataCount) {
        Bundle args = new Bundle();
        args.putString(Constant.USERID, userId);
        args.putInt(Constant.DATA_ITEM_COUNT, dataCount);
        UserPinFragment fragment = new UserPinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public UserPinAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void initAdapter() {
        mAdapter = new UserPinAdapter(mContext, isMe);

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
                    case R.id.linearlayout_user_pin:
                        float ratioTemp = mAdapter.getItem(i).getFile().getWidth() /
                                ((float) mAdapter.getItem(i).getFile().getHeight());
                        ImageDetailActivity.launch(getActivity(),
                                mAdapter.getItem(i).getPin_id(),
                                ratioTemp,
                                (SimpleDraweeView) view.findViewById(R.id.img_user_item_pin));
                        break;
                }
            }
        });
    }

    @Override
    public void httpForFirstTime() {

        new RetrofitClient().createService(UserApi.class)
                .httpUserPinsService(mAuthorization, mUserId, Constant.LIMIT)
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
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(mRefreshListener == null);
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
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
                .httpUserPinsMaxService(mAuthorization, mUserId, mMaxId, Constant.LIMIT)
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
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
    private void setMaxId(List<PinsBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getPin_id();
                mDataCountLastRequested = list.size();
            }
        }
    }

}
