package com.glooory.huaban.module.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.AllApi;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.Base64;
import com.glooory.huaban.util.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/8/28 0028.
 */
public class PinsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mAuthorization;
    private PinQuickAdapter mAdapter;
    private int mMaxId;
    private final int PAGE_SIZE = 20;

    Subscription subscription;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swiperefresh_recycler, container, false);
        ButterKnife.bind(this, view);
        if (false) {
            //// TODO: 2016/8/31 0031 检查是否登录
        } else {
            mAuthorization = Base64.CLIENTINFO;
        }
        initViews();
        getHttpFirst();
        return view;
    }

    private void initViews() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {

        mAdapter = new PinQuickAdapter(getContext());
        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(getContext()).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
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
                        Toast.makeText(getContext(), "you just clicked the img", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_card_via_ll:
                        Toast.makeText(getContext(), "via info", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getHttpFirst();
    }

    /**
     * 第一次加载数据
     */
    private void getHttpFirst() {
        mSwipeRefreshLayout.setRefreshing(true);

        subscription = RetrofitClient.createService(AllApi.class)
                .httpAllService(mAuthorization, 20)
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkException(e);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        setMaxId(list);
                        mAdapter.setNewData(list);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
        
    }

    /**
     * 后续上拉自动加载数据
     * @param maxId
     */
    public void getHttpMaxId(int maxId) {

        subscription = RetrofitClient.createService(AllApi.class)
                .httpAllMaxService(mAuthorization, maxId, 20)
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        mAdapter.showLoadMoreFailedView();
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        setMaxId(list);
                        mAdapter.addData(list);
                    }
                });

    }

    @Override
    public void onLoadMoreRequested() {
        getHttpMaxId(mMaxId);
    }

    /**
     * 保存本次请求的maxId值，后续请求数据会带上
     * @param list
     */
    private void setMaxId(List<PinsBean> list) {
        mMaxId = list.get(list.size() - 1).getPin_id();
    }

    protected void checkException(Throwable throwable) {
        NetworkUtils.checkHttpException(getContext(), throwable, mRecyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
