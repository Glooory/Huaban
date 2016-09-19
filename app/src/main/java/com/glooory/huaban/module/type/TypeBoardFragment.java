package com.glooory.huaban.module.type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.TypeBoardAdapter;
import com.glooory.huaban.api.OperateApi;
import com.glooory.huaban.api.TypeApi;
import com.glooory.huaban.base.BaseTypeFragment;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.board.BoardActivity;
import com.glooory.huaban.module.board.FollowBoardOperateBean;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.module.user.UserBoardItemBean;
import com.glooory.huaban.module.user.UserBoardListBean;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/17 0017 10:20.
 */
public class TypeBoardFragment extends BaseTypeFragment {
    private TypeBoardAdapter mAdapter;

    public static TypeBoardFragment newInstance(String authorization, String type) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putString(TypeActivity.KEY_FRAGMENT_TYPE, type);
        TypeBoardFragment fragment = new TypeBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initAdapter() {
        mAdapter = new TypeBoardAdapter(mContext);


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
                        BoardActivity.launch(getActivity(),
                                mAdapter.getData().get(i),
                                mAdapter.getData().get(i).getUser().getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.img_card_image));
                        break;
                    case R.id.tv_board_operate:
//                        if (isMe) {
//                            showEditDialog(String.valueOf(mAdapter.getItem(i).getBoard_id()),
//                                    mAdapter.getItem(i).getTitle(),
//                                    mAdapter.getItem(i).getDescription(),
//                                    mAdapter.getItem(i).getCategory_id());
//                        } else {
                        if (mIsLogin) {
                            actionBoardFollow(mAdapter.getItem(i).getBoard_id(), mAdapter.getItem(i).isFollowing(), i);
                        } else {
                            ((TypeActivity) getActivity()).showLoginMessage();
                        }
//                        }
                        break;
                    case R.id.rl_card_type_board_user:
                        UserActivity.launch(getActivity(),
                                String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUser().getUsername(),
                                ((SimpleDraweeView) view.findViewById(R.id.img_card_type_board_avatar)));
                }
            }
        });
    }

    @Override
    public TypeBoardAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void httpForFirstTime() {

        Subscription s = RetrofitClient.createService(TypeApi.class)
                .httpTypeBoardsService(mAuthorization, mType, Constant.LIMIT)
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
                    public void onNext(List<UserBoardItemBean> list) {
                        saveMaxId(list);
                        mAdapter.setNewData(list);
                        checkIfAddFooter();
                    }
                });
        addSubscription(s);
    }

    @Override
    public void httpForMoreData() {

        Subscription s = RetrofitClient.createService(TypeApi.class)
                .httpTypeBoardsMaxService(mAuthorization, mType, mMaxId, Constant.LIMIT)
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
                    public void onNext(List<UserBoardItemBean> list) {
                        saveMaxId(list);
                        mAdapter.addData(list);
                        checkIfAddFooter();
                    }
                });
        addSubscription(s);
    }

    private void saveMaxId(List<UserBoardItemBean> list) {
        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getBoard_id();
                mDataCountLastRequested = list.size();
            }
        }
    }

    /**
     * 针对画板的关注和取消关注操作
     *
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
                                following ? R.string.follow_operate_success : R.string.unfollow_operate_success,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
