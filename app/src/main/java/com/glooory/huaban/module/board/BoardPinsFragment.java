package com.glooory.huaban.module.board;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.glooory.huaban.adapter.UserPinAdapter;
import com.glooory.huaban.api.BoardApi;
import com.glooory.huaban.base.BaseFragment;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.net.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.util.Constant;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/7 0007 17:24.
 */
public class BoardPinsFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    //触发自动加载的基数
    private int PAGE_SIZE = 20;
    //当前已经加载的数据的数量
    private int mCurrentCount;
    //当前画板的总采集数量
    private int mPinsTotal;
    private RecyclerView mRecyclerView;
    private UserPinAdapter mAdapter;
    //没有更多内容的view
    private View mFooterView;
    //后续自动加载数据需要的PinId
    private int mMaxId;
    private Context mContext;
    private int mBoardId;
    private String mAuthorization;
    private boolean mIsMe;

    public static BoardPinsFragment newInstance(String authorization, int boardId, int pinCount, boolean isMe) {
        Bundle args = new Bundle();
        args.putInt(Constant.BOARD_ID, boardId);
        args.putInt(Constant.PIN_COUNT, pinCount);
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putBoolean(Constant.ISME, isMe);
        BoardPinsFragment fragment = new BoardPinsFragment();
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
        mBoardId = (int) getArguments().get(Constant.BOARD_ID);
        mPinsTotal = (int) getArguments().get(Constant.PIN_COUNT);
        mAuthorization = (String) getArguments().get(Constant.AUTHORIZATION);
        mIsMe = getArguments().getBoolean(Constant.ISME);
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
        mAdapter = new UserPinAdapter(mContext, mIsMe);

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
                    case R.id.linearlayout_user_pin:
                        float ratioTemp = mAdapter.getItem(i).getFile().getWidth() /
                                ((float) mAdapter.getItem(i).getFile().getHeight());
                        ImageDetailActivity.launch(getActivity(),
                                mAdapter.getItem(i).getPin_id(),
                                ratioTemp,
                                (SimpleDraweeView) view.findViewById(R.id.img_user_item_pin));
                        break;
                    case R.id.imgbtn_delete:
                        Toast.makeText(mContext, "delete successfully", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

//        //如果当前采集是自己的采集设置longclicklistener
//        if (mIsMe) {
//            mRecyclerView.addOnItemTouchListener(new OnItemChildLongClickListener() {
//                @Override
//                public void SimpleOnItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                    if (view.getId() == R.id.card_user_pin) {
//                        Logger.d("longclicklistener");
//                        view.findViewById(R.id.imgbtn_delete).setVisibility(View.VISIBLE);
//                        view.setEnabled(false);
//                    }
//                }
//            });
//        }
    }

    private void httpForFirst() {

        Subscription s = RetrofitClient.createService(BoardApi.class)
                .httpForBoardPinService(mAuthorization, mBoardId, Constant.LIMIT)
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
                        saveMaxId(list);
                        mAdapter.setNewData(list);
                        mCurrentCount = mAdapter.getData().size();
                        checkIfAddFooter();
                    }
                });
        addSubscription(s);
    }

    private void httpForMore() {

        Subscription s = RetrofitClient.createService(BoardApi.class)
                .httpForBoardPinsMaxService(mAuthorization, mBoardId, mMaxId, Constant.LIMIT)
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
                        saveMaxId(list);
                        mAdapter.addData(list);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });
        addSubscription(s);
    }

    private void saveMaxId(List<PinsBean> list) {

        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getPin_id();
            }
        }

    }

    public void checkIfAddFooter() {
        if (mPinsTotal < PAGE_SIZE) {
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
                if (mCurrentCount >= mPinsTotal) {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(mFooterView);
                } else {
                    httpForMore();
                }
            }
        });

    }

}
