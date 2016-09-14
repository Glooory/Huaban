package com.glooory.huaban.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.UserBoardAdapter;
import com.glooory.huaban.api.OperateApi;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseUserFragment;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.board.BoardActivity;
import com.glooory.huaban.module.board.FollowBoardOperateBean;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/3 0003 18:18.
 */
public class UserBoardFragment extends BaseUserFragment{
    private UserBoardAdapter mAdapter;
    private int mCurrentCount;
    private String mUserName;

    public static UserBoardFragment newInstance(String userId, int boardCount, String userName) {
        Bundle args = new Bundle();
        args.putString(Constant.USERID, userId);
        args.putInt(Constant.DATA_ITEM_COUNT, boardCount);
        args.putString(Constant.USERNAME, userName);
        UserBoardFragment fragment = new UserBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserName = getArguments().getString(Constant.USERNAME);
    }

    @Override
    public UserBoardAdapter getMAdapter() {
        initAdapter();
        return mAdapter;
    }

    private void initAdapter() {
        mAdapter = new UserBoardAdapter(mContext, isMe);


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
                    case R.id.linearlayout_image:
                        BoardActivity.launch(getActivity(), mAdapter.getData().get(i), mUserName,(SimpleDraweeView) view.findViewById(R.id.img_card_image));
                        break;
                    case R.id.relativelayout_board_operate:
                        if (isMe) {
                            // TODO: 2016/9/14 0014 edit board action
                        } else {
                            actionBoardFollow(mAdapter.getItem(i).getBoard_id(), mAdapter.getItem(i).isFollowing(), i);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void httpForFirstTime() {

        RetrofitClient.createService(UserApi.class)
                .httpUserBoardService(mAuthorization, mUserId, Constant.LIMIT)
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(new Func1<List<UserBoardItemBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserBoardItemBean> userBoardItemBeen) {
                        return userBoardItemBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
                    @Override
                    public void onCompleted() {
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        Logger.d(mRefreshListener != null);
                        if (mRefreshListener != null) {
                            mRefreshListener.requestRefreshDone();
                        }
                    }

                    @Override
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        setMaxId(userBoardItemBeen);
                        mAdapter.setNewData(userBoardItemBeen);
                        mCurrentCount = mAdapter.getData().size();
                        checkIfAddFooter();
                    }
                });

    }

    private void moreHttpRequest() {

         new RetrofitClient().createService(UserApi.class)
                .httpUserBoardMaxService(mAuthorization, mUserId, mMaxId, Constant.LIMIT)
                .map(new Func1<UserBoardListBean, List<UserBoardItemBean>>() {
                    @Override
                    public List<UserBoardItemBean> call(UserBoardListBean userBoardListBean) {
                        return userBoardListBean.getBoards();
                    }
                })
                .filter(new Func1<List<UserBoardItemBean>, Boolean>() {
                    @Override
                    public Boolean call(List<UserBoardItemBean> userBoardItemBeen) {
                        return userBoardItemBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserBoardItemBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserBoardItemBean> userBoardItemBeen) {
                        setMaxId(userBoardItemBeen);
                        mAdapter.addData(userBoardItemBeen);
                        mCurrentCount = mAdapter.getData().size();
                    }
                });
    }

    /**
     * 保存这次请求的maxId ， 后面联网请求数据要带上
     * @param beans
     */
    private void setMaxId(List<UserBoardItemBean> beans) {
        mMaxId = beans.get(beans.size() - 1).getBoard_id();
    }

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

    /**
     * 针对画板的关注和取消关注操作
     * @param isFollowing
     */
    private void actionBoardFollow(int boardId, final boolean isFollowing, final int position) {
        String operateString = isFollowing ? Constant.OPERATEUNFOLLOW : Constant.OPERATEFOLLOW;

        new RetrofitClient().createService(OperateApi.class)
                .httpFollowBoardService(mAuthorization, boardId, operateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowBoardOperateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, getString(R.string.operate_request_failed), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(FollowBoardOperateBean followBoardOperateBean) {
                        boolean following = !isFollowing;
                        mAdapter.getItem(position).setFollowing(following);
                        mAdapter.notifyItemChanged(position);
                        Toast.makeText(mContext,
                                isFollowing ? getString(R.string.unfollow_operate_success) : getString(R.string.follow_operate_success),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 滑动到底部的自动加载数据的回调
     */
    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCount >= mDateItemCount) {
                    mAdapter.loadComplete();
                    mAdapter.addFooterView(mFooterView);
                } else {
                    moreHttpRequest();
                }
            }
        });
    }

    @Override
    public void refreshData() {
        httpForFirstTime();
    }

}
