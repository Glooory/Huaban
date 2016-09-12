package com.glooory.huaban.module.imagedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.ImageDetailApi;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/11 0011 18:15.
 */
public class RecommendFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private int mPinId;
    private PinQuickAdapter mAdapter;
    private Context mContext;
    private int mPage = 1;
    private String mAuthorization;
    private RecyclerView mRecyclerView;
    private final int PAGE_SIZE = 20;

    public static RecommendFragment newInstance(String authorization, int pinId) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putInt(Constant.PIN_ID, pinId);

        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorization = getArguments().getString(Constant.AUTHORIZATION);
        mPinId = getArguments().getInt(Constant.PIN_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_user_recyclerview, container, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    private void initAdapter() {

        mAdapter = new PinQuickAdapter(mContext);

        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
        mAdapter.setLoadingView(loadMoreView);

        //当当前position等于PAGE_SIZE 时，就回调用onLoadMoreRequested() 自动加载下一页数据
        mAdapter.openLoadMore(PAGE_SIZE);
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.item_card_pin_img_ll:
                        // TODO: 2016/9/4 0004 launch ImageDetailActivity
                        Toast.makeText(getContext(), "you just clicked the img", Toast.LENGTH_SHORT).show();
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

    private void httpForFirst() {

        new RetrofitClient().createService(ImageDetailApi.class)
                .httpRecommendService(mAuthorization, mPinId, mPage, Constant.LIMIT)
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
                        mAdapter.setNewData(list);
                        mPage++;
                    }
                });

    }

    private void httpForMore() {

        new RetrofitClient().createService(ImageDetailApi.class)
                .httpRecommendService(mAuthorization, mPinId, mPage, Constant.LIMIT)
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
                        mAdapter.addData(list);
                        mPage++;
                    }
                });

    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                httpForMore();
            }
        });
    }
}
