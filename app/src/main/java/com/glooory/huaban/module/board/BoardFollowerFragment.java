package com.glooory.huaban.module.board;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.FollowerAdapter;
import com.glooory.huaban.api.BoardApi;
import com.glooory.huaban.entity.FollowerBean;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/7 0007 22:29.
 */
public class BoardFollowerFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    //触发自动加载的基数
    private int PAGE_SIZE = 20;
    //当前已经加载的数据的数量
    private int mCurrentCount;
    //当前画板的总采集数量
    private int mFollowersTotal;
    private RecyclerView mRecyclerView;
    private FollowerAdapter mAdapter;
    //没有更多内容的view
    private View mFooterView;
    //后续自动加载数据需要的PinId
    private int mMaxSeq;
    private Context mContext;
    private int mBoardId;
    private String mAuthorization;

    public static BoardFollowerFragment newInstance(String authorization, int boardId, int followerCount) {

        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putInt(Constant.BOARD_ID, boardId);
        args.putInt(Constant.FOLLOWER_COUNT, followerCount);
        BoardFollowerFragment fragment = new BoardFollowerFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorization = (String) getArguments().getString(Constant.AUTHORIZATION);
        mBoardId = getArguments().getInt(Constant.BOARD_ID);
        mFollowersTotal = getArguments().getInt(Constant.FOLLOWER_COUNT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_user_recyclerview, container, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFooterView = inflater.inflate(R.layout.view_no_more_data_footer, container, false);
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
        httpForFirst();
        return mRecyclerView;

    }

    private void initAdapter() {

        mAdapter = new FollowerAdapter(mContext);

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
                    case R.id.card_follower_ripple:
                        UserActivity.launch(getActivity(),
                                mAdapter.getItem(i).getUser_id(),
                                mAdapter.getItem(i).getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.img_card_follower_avatar));
                        break;
                }
            }
        });

    }

    private void httpForFirst() {

        new RetrofitClient().createService(BoardApi.class)
                .httpForBoardFollowerService(mAuthorization, mBoardId, Constant.LIMIT)
                .map(new Func1<FollowerBean, List<FollowerBean.FollowersBean>>() {
                    @Override
                    public List<FollowerBean.FollowersBean> call(FollowerBean followerBean) {
                        return followerBean.getFollowers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FollowerBean.FollowersBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<FollowerBean.FollowersBean> followersBeen) {
                        setMaxSeq(followersBeen);
                        mAdapter.setNewData(followersBeen);
                        mCurrentCount = mAdapter.getData().size();
                        checkIfAddFooter();
                    }
                });

    }

    private void httpForMore() {

        new RetrofitClient().createService(BoardApi.class)
                .httpForBoardFollowerMaxService(mAuthorization, mBoardId, mMaxSeq, Constant.LIMIT)
                .map(new Func1<FollowerBean, List<FollowerBean.FollowersBean>>() {
                    @Override
                    public List<FollowerBean.FollowersBean> call(FollowerBean followerBean) {
                        return followerBean.getFollowers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FollowerBean.FollowersBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<FollowerBean.FollowersBean> followersBeen) {
                        setMaxSeq(followersBeen);
                        mAdapter.addData(followersBeen);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });

    }

    private void setMaxSeq(List<FollowerBean.FollowersBean> beans) {
        if (beans != null) {
            if (beans.size() > 0) {
                mMaxSeq = beans.get(beans.size() - 1).getSeq();
            }
        }
    }

    public void checkIfAddFooter() {
        if (mFollowersTotal < PAGE_SIZE) {
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
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mFollowersTotal < PAGE_SIZE) {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(mFooterView);
                } else {
                    httpForMore();
                }
            }
        });
    }
}
