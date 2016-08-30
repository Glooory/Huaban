package com.glooory.huaban.module.Main;

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

import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinCardAdapter;
import com.glooory.huaban.api.AllApi;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.util.Base64;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/8/28 0028.
 */
public class PinsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private PinCardAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("Fragment onCreateView() excuted");
        View view = inflater.inflate(R.layout.fragment_swiperefresh_recycler, container, false);
        ButterKnife.bind(this, view);
        initViews();
        getHttpMaxId(0);
        return view;
    }

    private void initViews() {
        Logger.d("Fragment initViews() excuted");
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PinCardAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {

    }

    private void getHttpMaxId(int max) {

        mSwipeRefreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://api.huaban.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Subscription subscription = retrofit.create(AllApi.class)
                .httpAllService(Base64.CLIENTINFO, 20)
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
                        Logger.d("subscrber onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("subscrber onError");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        Logger.d(String.valueOf(list.size()));
                        mAdapter.setPinsList(list);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }
}
