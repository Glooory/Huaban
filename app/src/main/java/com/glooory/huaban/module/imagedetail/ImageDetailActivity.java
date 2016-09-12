package com.glooory.huaban.module.imagedetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.adapter.PinQuickAdapter;
import com.glooory.huaban.api.ImageDetailApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.board.BoardActivity;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.module.user.UserBoardItemBean;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.TimeUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/10 0010 19:43.
 */
public class ImageDetailActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.toolbar_image_detail)
    Toolbar mToolbar;
    @BindView(R.id.img_image_detail)
    SimpleDraweeView mImageDetail;
    private LinearLayout mLlPin;
    private ShineButton mSbtnlPin;
    private TextView mTvPincount;
    private LinearLayout mLllLike;
    private ShineButton mSbtnlLike;
    private TextView mTvLikecount;
    private TextView mTvDes;
    private RelativeLayout mRlUserBar;
    private SimpleDraweeView mImgAvatar;
    private TextView mTvUserName;
    private TextView mTvPinTime;
    private RelativeLayout mRlBoardBar;
    private SimpleDraweeView mImgBoard;
    private TextView mTvBoardname;
    @BindView(R.id.recycler_view_image_detail)
    RecyclerView mRecyclerView;

    @BindString(R.string.format_url_image_big)
    String mBigImgUrl;
    @BindString(R.string.format_url_image_small)
    String mSmallImgUrl;
    @BindString(R.string.format_gather_number)
    String mGatherFormat;
    @BindString(R.string.format_like_number)
    String mLikeFormat;

    private int mPinId;
    private String mUserName;
    private int mUserId;
    private String mBoard;
    private boolean isLiked;
    private float mRatio;
    private UserBoardItemBean mBoardBean;
    private PinQuickAdapter mAdapter;
    private int mPage = 1;
    private int PAGE_SIZE = 20;

    public static void launch(Activity activity, int pinId, float ratio, SimpleDraweeView image) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        intent.putExtra(Constant.PIN_ID, pinId);
        intent.putExtra(Constant.RATIO, ratio);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setTransitionName(activity.getString(R.string.image_tran));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, image, activity.getString(R.string.image_tran)
            ).toBundle());
        } else {
            activity.startActivity(intent);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected String getTAG() {
        return "ImageDetailActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mPinId = getIntent().getExtras().getInt(Constant.PIN_ID);
        mRatio = getIntent().getExtras().getFloat(Constant.RATIO);

        initAdapter();
        initView();
        mRecyclerView.setAdapter(mAdapter);
        httpForImg();
        httpForFirst();
    }

    private void initView() {


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (mRatio > 0) {
            mImageDetail.setAspectRatio(mRatio);
        }

        //设置点击事件监听
        RxView.clicks(mLlPin)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Logger.d("pin linearlayout");
                        mSbtnlPin.performClick();
                        // TODO: 2016/9/11 0011 pin operation
                    }
                });

        RxView.clicks(mLllLike)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Logger.d("like linearlayout");
                        mSbtnlLike.performClick();
                        // TODO: 2016/9/11 0011 pin operation
                    }
                });

        RxView.clicks(mRlUserBar)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        UserActivity.launch(ImageDetailActivity.this,
                                String.valueOf(mUserId),
                                mUserName,
                                mImgAvatar);
                    }
                });

        RxView.clicks(mRlBoardBar)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        BoardActivity.launch(ImageDetailActivity.this,
                                mBoardBean,
                                mUserName,
                                mImgBoard);
                    }
                });

    }

    private void initAdapter() {

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new PinQuickAdapter(mContext);

        //设置headerview
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.view_image_detail_header, mRecyclerView, false);
        mLlPin = ButterKnife.findById(headerView, R.id.ll_iamge_detail_pin);
        mSbtnlPin = ButterKnife.findById(headerView, R.id.sbtn_image_detail_pin);
        mTvPincount = ButterKnife.findById(headerView, R.id.tv_image_detail_pincount);
        mLllLike = ButterKnife.findById(headerView, R.id.ll_iamge_detail_like);
        mSbtnlLike = ButterKnife.findById(headerView, R.id.sbtn_image_detail_like);
        mTvLikecount = ButterKnife.findById(headerView, R.id.tv_image_detail_likecount);
        mTvDes = ButterKnife.findById(headerView, R.id.tv_image_detail_des);
        mRlUserBar = ButterKnife.findById(headerView, R.id.rl_iamge_detail_user_bar);
        mImgAvatar = ButterKnife.findById(headerView, R.id.img_image_detail_avatar);
        mTvUserName = ButterKnife.findById(headerView, R.id.tv_image_detail_username);
        mTvPinTime = ButterKnife.findById(headerView, R.id.tv_image_detail_pin_time);
        mRlBoardBar = ButterKnife.findById(headerView, R.id.rl_iamge_detail_board_bar);
        mImgBoard = ButterKnife.findById(headerView, R.id.img_image_detail_board);
        mTvBoardname = ButterKnife.findById(headerView, R.id.tv_image_detail_boardname);
        mAdapter.addHeaderView(headerView);

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
                        float ratioTemp = mAdapter.getItem(i).getFile().getWidth() / ((float) mAdapter.getItem(i).getFile().getHeight());
                        ImageDetailActivity.launch(ImageDetailActivity.this,
                                mAdapter.getItem(i).getPin_id(),
                                ratioTemp,
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_img));
                        break;
                    case R.id.item_card_via_ll:
                        UserActivity.launch(ImageDetailActivity.this,
                                String.valueOf(mAdapter.getItem(i).getUser_id()),
                                mAdapter.getItem(i).getUser().getUsername(),
                                (SimpleDraweeView) view.findViewById(R.id.item_card_pin_avaterimg));
                        break;
                }
            }
        });

    }

    private void httpForImg() {

        new RetrofitClient().createService(ImageDetailApi.class)
                .httpPinDetailService(mAuthorization, mPinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinDetailBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(PinDetailBean pinDetailBean) {
                        setUpViews(pinDetailBean);
                    }
                });

    }

    private void setUpViews(PinDetailBean pinDetailBean) {

        //保存需要的信息
        mUserId = pinDetailBean.getPin().getUser().getUser_id();
        mUserName = pinDetailBean.getPin().getUser().getUsername();
        mBoardBean = pinDetailBean.getPin().getBoard();

        //加载图片
        if (mRatio <= 0) {
            float ratio = pinDetailBean.getPin().getFile().getWidth() /
                    ((float) pinDetailBean.getPin().getFile().getHeight());
            mImageDetail.setAspectRatio(ratio);
        }
        mImageDetail.setVisibility(View.VISIBLE);
        String pinKey = pinDetailBean.getPin().getFile().getKey();
        if (!TextUtils.isEmpty(pinKey)) {
            Drawable mProgress = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
            Drawable retryDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_retry_36dp, R.color.tint_list_grey);
            Drawable failDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_load_failed_36dp, R.color.tint_list_grey);
            new FrescoLoader.Builder(mContext, mImageDetail, String.format(mBigImgUrl, pinKey))
                    .setProgressbarImage(mProgress)
                    .setRetryImage(retryDrawable)
                    .setFailureIamge(failDrawable)
                    .setControllerListenrr(new BaseControllerListener(){
                        @Override
                        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                            super.onFinalImageSet(id, imageInfo, animatable);
                            if (animatable != null) {
                                animatable.start();
                            }
                        }
                    })
                    .build();
        }

        //是否已经喜欢以及喜欢和采集数量
        isLiked = pinDetailBean.getPin().isLiked();
        if (isLiked) {
            mSbtnlLike.setChecked(true);
        }
        mTvPincount.setText(String.format(mGatherFormat, pinDetailBean.getPin().getRepin_count()));
        mTvLikecount.setText(String.format(mLikeFormat, pinDetailBean.getPin().getLike_count()));

        //设置图片的描述信息
        if (!TextUtils.isEmpty(pinDetailBean.getPin().getRaw_text())) {
            mTvDes.setText(pinDetailBean.getPin().getRaw_text());
        } else {
            mTvDes.setVisibility(View.GONE);
        }

        //设置用户的头像和名称
        String avatarKey = pinDetailBean.getPin().getUser().getAvatar().getKey();
        if (!TextUtils.isEmpty(avatarKey)) {
            new FrescoLoader.Builder(mContext, mImgAvatar, String.format(mSmallImgUrl, avatarKey))
                    .setIsCircle(true)
                    .build();
        }
        mTvUserName.setText(mUserName);
        mTvPinTime.setText(TimeUtils.getTimeDifference(pinDetailBean.getPin().getCreated_at(), System.currentTimeMillis()));

        //设置采集到画板的信息
        if (pinDetailBean.getPin().getBoard().getPins() != null) {
            String boardKey = pinDetailBean.getPin().getBoard().getPins().get(0).getFile().getKey();
            if (!TextUtils.isEmpty(boardKey)) {
                new FrescoLoader.Builder(mContext, mImgBoard, String.format(mSmallImgUrl, boardKey))
                        .build();
            }
        }
        mTvBoardname.setText(pinDetailBean.getPin().getBoard().getTitle());

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
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
