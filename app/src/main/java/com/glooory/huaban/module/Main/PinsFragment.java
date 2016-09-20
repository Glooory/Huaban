package com.glooory.huaban.module.main;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.AllApi;
import com.glooory.huaban.base.BaseFragment;
import com.glooory.huaban.entity.ListPinsBean;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
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
public class PinsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    public static final int HOME_FRAGMENT_INDEX = 0;
    public static final int NEWEST_FRAGMENT_INDEX = 1;
    public static final int POPULAR_FRAGMENT_INDEX = 2;
    public static final String FRAGMENT_TYPE_INDEX = "fragment_type_index";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mAuthorization;
    private PinQuickAdapter mAdapter;
    private int mMaxId;
    private final int PAGE_SIZE = 20;
    private Context mContext;
    private Resources mResources;
    private int mTypeIndex;

    public static PinsFragment newInstance(String authorization, int typeIndex) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putInt(FRAGMENT_TYPE_INDEX, typeIndex);
        PinsFragment fragment = new PinsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mResources = context.getResources();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorization = getArguments().getString(Constant.AUTHORIZATION);
        mTypeIndex = getArguments().getInt(FRAGMENT_TYPE_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swiperefresh_recycler, container, false);
        ButterKnife.bind(this, view);
        initViews();
        httpFirstTime();
        return view;
    }

    private void initViews() {
        mSwipeRefreshLayout.setColorSchemeColors(mResources.getColor(R.color.blue_g_i), mResources.getColor(R.color.red_g_i),
                mResources.getColor(R.color.yellow_g_i), mResources.getColor(R.color.green_g_i));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {

        mAdapter = new PinQuickAdapter(mContext);
        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.custom_loadmore_view, mSwipeRefreshLayout, false);
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
    public void onRefresh() {
        httpFirstTime();
    }

    /**
     * 第一次加载数据
     */
    private void httpFirstTime() {
        mSwipeRefreshLayout.setRefreshing(true);

        switch (mTypeIndex) {
            case HOME_FRAGMENT_INDEX:
                httpHomeFirst();
                break;
            case NEWEST_FRAGMENT_INDEX:
                httpNewestFirst();
                break;
            case POPULAR_FRAGMENT_INDEX:
                httpPopularFirst();
                break;
        }

    }

    /**
     * 后续上拉自动加载数据
     */
    public void httpForMoreData() {

        switch (mTypeIndex) {
            case HOME_FRAGMENT_INDEX:
                httpHomeMore();
                break;
            case NEWEST_FRAGMENT_INDEX:
                httpNewestMore();
                break;
            case POPULAR_FRAGMENT_INDEX:
                httpPopularMore();
                break;
        }

    }

    /**
     * 首次加载首页模块的数据
     */
    private void httpHomeFirst() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpHomePinsService(mAuthorization, Constant.LIMIT)
                .map(new Func1<ListPinsBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
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
        addSubscription(s);
    }

    /**
     * 首次加载最新模块的数据
     */
    private void httpNewestFirst() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpAllService(mAuthorization, Constant.LIMIT)
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
        addSubscription(s);
    }

    /**
     * 第一次请求热门采集
     */
    private void httpPopularFirst() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpPopularPinsService(mAuthorization, Constant.LIMIT)
                .map(new Func1<ListPinsBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
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
        addSubscription(s);
    }

    /**
     * 后续自动加载首页模块的更多数据
     */
    private void httpHomeMore() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpHomePinsMaxService(mAuthorization, mMaxId, Constant.LIMIT)
                .map(new Func1<ListPinsBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
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
                        mAdapter.showLoadMoreFailedView();
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        setMaxId(list);
                        mAdapter.addData(list);
                    }
                });
        addSubscription(s);
    }

    /**
     * 后续自动加载最新模块的更多数据
     */
    private void httpNewestMore() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpAllMaxService(mAuthorization, Constant.LIMIT, mMaxId)
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
                        checkException(e);
                        mAdapter.showLoadMoreFailedView();
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        setMaxId(list);
                        mAdapter.addData(list);
                    }
                });
        addSubscription(s);
    }

    /**
     * 后续自动加载更多热门采集
     */
    private void httpPopularMore() {

        Subscription s = RetrofitClient.createService(AllApi.class)
                .httpPopularPinsMaxService(mAuthorization, mMaxId, Constant.LIMIT)
                .map(new Func1<ListPinsBean, List<PinsBean>>() {
                    @Override
                    public List<PinsBean> call(ListPinsBean listPinsBean) {
                        return listPinsBean.getPins();
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
                        mAdapter.showLoadMoreFailedView();
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        setMaxId(list);
                        mAdapter.addData(list);
                    }
                });
        addSubscription(s);
    }

    @Override
    public void onLoadMoreRequested() {
        httpForMoreData();
    }

    /**
     * 保存本次请求的maxId值，后续请求数据会带上
     *
     * @param list
     */
    private void setMaxId(List<PinsBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getPin_id();
            }
        }
    }

    protected void checkException(Throwable throwable) {
        NetworkUtils.checkHttpException(getContext(), throwable, mRecyclerView);
    }

}
