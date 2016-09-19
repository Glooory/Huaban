package com.glooory.huaban.module.type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.TypeUserAdapter;
import com.glooory.huaban.api.OperateApi;
import com.glooory.huaban.api.TypeApi;
import com.glooory.huaban.base.BaseTypeFragment;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.util.Constant;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/17 0017 17:32.
 */
public class TypeUserFragment extends BaseTypeFragment {
    private TypeUserAdapter mAdapter;
    /**
     * 请求分类推荐的用户时，每次最多返回12个数据， 即使请求方法中带的Limit参数大于12
     */
    private final int LIMIT = 12;

    public static TypeUserFragment newInstance(String authorization, String type) {
        Bundle args = new Bundle();
        args.putString(Constant.AUTHORIZATION, authorization);
        args.putString(TypeActivity.KEY_FRAGMENT_TYPE, type);
        TypeUserFragment fragment = new TypeUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initAdapter() {

        mAdapter = new TypeUserAdapter(mContext);


        //设置上滑自动建在的正在加载更多的自定义View
        View loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.custom_loadmore_view, mRecyclerView, false);
        mAdapter.setLoadingView(loadMoreView);

        //当当前position等于PAGE_SIZE 时，就回调用onLoadMoreRequested() 自动加载下一页数据
        mAdapter.openLoadMore(LIMIT);

        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.card_follower_ripple:
                        UserActivity.launch(getActivity(),
                                String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUser().getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.img_card_follower_avatar));
                        break;
                    case R.id.tv_card_user_follow_operate:
                        if (mIsLogin) {
                            actionFollowUser((TextView) view, mAdapter.getItem(i), i);
                        } else {
                            ((TypeActivity) getActivity()).showLoginMessage();
                        }
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
    public TypeUserAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void httpForFirstTime() {

        Subscription s = RetrofitClient.createService(TypeApi.class)
                .httpTypeUsersService(mAuthorization, mType, LIMIT)
                .map(new Func1<TypeUserListBean, List<TypeUserListBean.PusersBean>>() {
                    @Override
                    public List<TypeUserListBean.PusersBean> call(TypeUserListBean typeUserListBean) {
                        return typeUserListBean.getPusers();
                    }
                })
                .filter(new Func1<List<TypeUserListBean.PusersBean>, Boolean>() {
                    @Override
                    public Boolean call(List<TypeUserListBean.PusersBean> pusersBeen) {
                        return pusersBeen.size() > 0;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TypeUserListBean.PusersBean>>() {
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
                    public void onNext(List<TypeUserListBean.PusersBean> pusersBeen) {
                        Logger.d(pusersBeen.size());
                        saveMaxId(pusersBeen);
                        mAdapter.setNewData(pusersBeen);
                        checkIfAddFooter();
                    }
                });
        addSubscription(s);
    }

    @Override
    public void httpForMoreData() {

        Subscription s = RetrofitClient.createService(TypeApi.class)
                .httpTypeUsersMaxService(mAuthorization, mType, mMaxId, LIMIT)
                .map(new Func1<TypeUserListBean, List<TypeUserListBean.PusersBean>>() {
                    @Override
                    public List<TypeUserListBean.PusersBean> call(TypeUserListBean typeUserListBean) {
                        return typeUserListBean.getPusers();
                    }
                })
                .filter(new Func1<List<TypeUserListBean.PusersBean>, Boolean>() {
                    @Override
                    public Boolean call(List<TypeUserListBean.PusersBean> pusersBeen) {
                        return pusersBeen.size() > 0;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TypeUserListBean.PusersBean>>() {
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
                    public void onNext(List<TypeUserListBean.PusersBean> pusersBeen) {
                        saveMaxId(pusersBeen);
                        mAdapter.addData(pusersBeen);
                    }
                });
        addSubscription(s);
    }

    private void saveMaxId(List<TypeUserListBean.PusersBean> list) {

        if (list != null) {
            if (list.size() > 0) {
                mMaxId = list.get(list.size() - 1).getUpdated_at();
                mDataCountLastRequested = list.size();
            }
        }
    }

    /**
     * 判断当前数据是否已经加载完毕和加没有更多了的FooterView
     */
    public void checkIfAddFooter() {
        if (mDataCountLastRequested < LIMIT) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    setAdapterLoadComplete();
                }
            });
        }
    }

    /**
     * Adapter上滑自动加载更多数据的回调
     */
    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mDataCountLastRequested < LIMIT) {
                    setAdapterLoadComplete();
                } else {
                    httpForMoreData();
                }
            }
        });

    }

    /**
     * 关注或取消关注 某用户
     */
    private void actionFollowUser(final TextView textView, final TypeUserListBean.PusersBean bean, final int position) {
        textView.setEnabled(false);

        String operateString = bean.getUser().getFollowing() == 1 ? Constant.OPERATEUNFOLLOW : Constant.OPERATEFOLLOW;

        new RetrofitClient().createService(OperateApi.class)
                .httpFollowUserService(mAuthorization, String.valueOf(bean.getUser_id()), operateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        textView.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.setEnabled(true);
                        Toast.makeText(mContext, getString(R.string.operate_request_failed), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        if (bean.getUser().getFollowing() == 1) {
                            bean.getUser().setFollowing(0);
                        } else {
                            bean.getUser().setFollowing(1);
                        }
                        Toast.makeText(mContext,
                                bean.getUser().getFollowing() == 1 ? R.string.follow_operate_success : R.string.unfollow_operate_success,
                                Toast.LENGTH_SHORT).show();
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }
}
