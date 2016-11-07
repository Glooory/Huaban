package com.glooory.huaban.module.imagedetail;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.glooory.huaban.api.OperateApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.entity.imagedetail.GatherInfoBean;
import com.glooory.huaban.entity.imagedetail.GatherResultBean;
import com.glooory.huaban.entity.imagedetail.LikePinOperateBean;
import com.glooory.huaban.entity.imagedetail.PinDetailBean;
import com.glooory.huaban.entity.user.UserBoardItemBean;
import com.glooory.huaban.module.board.BoardActivity;
import com.glooory.huaban.module.user.PinEditDialogFragment;
import com.glooory.huaban.module.user.UserActivity;
import com.glooory.huaban.net.FrescoLoader;
import com.glooory.huaban.net.RetrofitClient;
import com.glooory.huaban.service.DownloadPinService;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.util.TimeUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/10 0010 19:43.
 */
public class ImageDetailActivity extends BaseActivity
        implements BaseQuickAdapter.RequestLoadMoreListener,
        GatherDiologFragment.GatherInfoListener {


    @BindView(R.id.toolbar_image_detail)
    Toolbar mToolbar;
    @BindView(R.id.img_image_detail)
    SimpleDraweeView mImageDetail;
    @BindView(R.id.coordinator_image_detail)
    CoordinatorLayout mCoordinator;
    private LinearLayout mLlPin;
    private ShineButton mSbtnlPin;
    private TextView mTvGathercount;
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
    @BindString(R.string.dialog_title_edit)
    String mStringEdit;

    private boolean mIsMinePin = false;
    private int mPinId;
    private String mUserName;
    private int mUserId;
    private boolean mIsLiked;
    private boolean mIsGathered = false;
    private float mRatio;
    private UserBoardItemBean mBoardBean;
    private PinQuickAdapter mAdapter;
    private int mPage = 1;
    private int PAGE_SIZE = 10;
    private int mLikeCount;
    private int mGatherCount;
    private String[] mBoardIds;
    private String mCurrentBoardId;
    private String mRawText;
    private String mGatherBelong;
    private String mPinKey;
    private String mPinType;
    private View mFooterView;

    public static void launch(Activity activity, int pinId, float ratio, SimpleDraweeView image) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        intent.putExtra(Constant.PIN_ID, pinId);
        intent.putExtra(Constant.RATIO, ratio);

        //太长的图使用Shared Elenment transition 时会crush
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ratio > 0.2) {
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

        httpForIsGathered();
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
            if (mRatio > 0.2) {
                mImageDetail.setAspectRatio(mRatio);
            } else {
                mImageDetail.setAspectRatio(0.3f);
            }
        }

        mFooterView = LayoutInflater.from(mContext).inflate(R.layout.view_no_more_data_footer, null);

        //设置点击事件监听
        RxView.clicks(mLlPin)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mSbtnlPin.performClick();
                    }
                });

        RxView.clicks(mSbtnlPin)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        actionGather();
                    }
                });

        RxView.clicks(mLllLike)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mSbtnlLike.performClick();
                    }
                });

        RxView.clicks(mSbtnlLike)
                .throttleFirst(Constant.THROTTDURATION, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        actionLike();
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
        mTvGathercount = ButterKnife.findById(headerView, R.id.tv_image_detail_pincount);
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

    /**
     * 加载图片
     */
    private void httpForImg() {

        Subscription s = RetrofitClient.createService(ImageDetailApi.class)
                .httpPinDetailService(mAuthorization, mPinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinDetailBean>() {
                    @Override
                    public void onCompleted() {
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
        addSubscription(s);
    }

    private void setUpViews(PinDetailBean pinDetailBean) {

        //保存需要的信息
        mUserId = pinDetailBean.getPin().getUser().getUser_id();
        mUserName = pinDetailBean.getPin().getUser().getUsername();
        mBoardBean = pinDetailBean.getPin().getBoard();
        mGatherCount = pinDetailBean.getPin().getRepin_count();
        mLikeCount = pinDetailBean.getPin().getLike_count();
        mCurrentBoardId = String.valueOf(pinDetailBean.getPin().getBoard_id());
        mRawText = pinDetailBean.getPin().getRaw_text();
        mPinKey = pinDetailBean.getPin().getFile().getKey();
        mPinType = pinDetailBean.getPin().getFile().getType();

        mIsMinePin = mUserName.equals(SPUtils.get(getApplicationContext(), Constant.USERNAME, ""));

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
                    .setControllerListenrr(new BaseControllerListener() {
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
        mIsLiked = pinDetailBean.getPin().isLiked();
        setUpLikeSbtn();
        mTvGathercount.setText(String.format(mGatherFormat, mGatherCount));
        setUpBtnApperence();
//        mTvLikecount.setText(String.format(mLikeFormat, mLikeCount));

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

    /**
     * 第一次请求相关推荐的数据
     */
    private void httpForFirst() {

        Subscription s = RetrofitClient.createService(ImageDetailApi.class)
                .httpRecommendService(mAuthorization, mPinId, mPage, PAGE_SIZE)
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

                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        mAdapter.setNewData(list);
                        mPage++;
                        checkIfAddFooter(list.size());
                    }
                });
        addSubscription(s);
    }

    /**
     * 相关推荐的上拉自动加载更多方法
     */
    private void httpForMore() {

        Subscription s = RetrofitClient.createService(ImageDetailApi.class)
                .httpRecommendService(mAuthorization, mPinId, mPage, PAGE_SIZE)
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

                    }

                    @Override
                    public void onNext(List<PinsBean> list) {
                        mAdapter.addData(list);
                        mPage++;
                        checkIfAddFooter(list.size());
                    }
                });
        addSubscription(s);
    }

    /**
     * 喜欢按钮的点击事件
     */
    private void actionLike() {
        if (isLogin) {

            if (mIsMinePin) {
                mSbtnlLike.setChecked(false);
                showPinEditDialog();
            } else {
                String operate = mIsLiked ? Constant.OPERATEUNLIKE : Constant.OPERATELIKE;
                Subscription s = RetrofitClient.createService(OperateApi.class)
                        .httpLikePinService(mAuthorization, mPinId, operate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<LikePinOperateBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mSbtnlLike.setChecked(false);
                                checkException(e, mCoordinator);
                            }

                            @Override
                            public void onNext(LikePinOperateBean likePinOperateBean) {
                                setUpWithLikeTv();
                                mIsLiked = !mIsLiked;
                                setUpLikeSbtn();
                            }
                        });
                addSubscription(s);
            }

        } else {
            mSbtnlLike.setChecked(false);
            showLoginSnackbar(ImageDetailActivity.this, mCoordinator);
        }
    }

    /**
     * 判断是显示喜欢还是编辑按钮
     */
    private void setUpBtnApperence() {
        if (mIsMinePin) {
            mSbtnlLike.setShapeResource(R.raw.edit);
            mTvLikecount.setText(mStringEdit);
        } else {
            mSbtnlLike.setShapeResource(R.raw.heart);
            mTvLikecount.setText(String.format(mLikeFormat, mLikeCount));
        }
    }

    /**
     * 采集按钮的点击事件
     */
    private void actionGather() {

        setUpPinSbtn();
        if (isLogin) {
            if (mIsGathered) {
                Snackbar snackbar = Snackbar.make(mCoordinator,
                        String.format(getString(R.string.text_gather_warning), mGatherBelong), Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.gather_anyway, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showGatherDialog();
                    }
                }).show();
            } else {
                showGatherDialog();
            }
        } else {
            showLoginSnackbar(ImageDetailActivity.this, mCoordinator);
            mSbtnlPin.setChecked(mIsGathered);
        }

    }

    /**
     * 根据登录状态和是否已经采集设置采集按钮的状态
     */
    private void setUpPinSbtn() {
        mSbtnlPin.setChecked(mIsGathered);
    }

    /**
     * 根据登录状态和是否已经喜欢设置喜欢按钮的状态
     */
    private void setUpLikeSbtn() {
        mSbtnlLike.setChecked(mIsLiked);
    }

    /**
     * 成功采集后更改采集数量
     */
    private void setUpWithGatherTv() {
        mGatherCount = mIsGathered ? --mGatherCount : ++mGatherCount;
        mTvGathercount.setText(String.format(mGatherFormat, mGatherCount));
    }

    /**
     * 成功喜欢后更新喜欢数量
     */
    private void setUpWithLikeTv() {
        mLikeCount = mIsLiked ? --mLikeCount : ++mLikeCount;
        mTvLikecount.setText(String.format(mLikeFormat, mLikeCount));
    }

    /**
     * 显示采集选项对话框
     */
    private void showGatherDialog() {

        String boardTitleArray = (String) SPUtils.get(getApplicationContext(), Constant.BOARDTITLEARRAY, "");
        String mBoardId = (String) SPUtils.get(getApplicationContext(), Constant.BOARDIDARRAY, "");

        String[] array = boardTitleArray != null ? boardTitleArray.split(Constant.SEPARATECOMMA) : new String[0];
        mBoardIds = mBoardId != null ? mBoardId.split(Constant.SEPARATECOMMA) : new String[0];
        GatherDiologFragment.create(mAuthorization, mPinId, mRawText, array).show(getSupportFragmentManager(), null);

    }

    //检查图片是否已经采集过
    private void httpForIsGathered() {

        Subscription s = RetrofitClient.createService(OperateApi.class)
                .httpGatheredInfoService(mAuthorization, mPinId, Constant.OPERATECHECK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatherInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GatherInfoBean gatherInfoBean) {
                        if (gatherInfoBean.getExist_pin() != null) {
                            mIsGathered = true;
                            mSbtnlPin.setChecked(true);
                            mGatherBelong = gatherInfoBean.getExist_pin().getBoard().getTitle();
                        }
                    }
                });
        addSubscription(s);
    }

    /**
     * 判断是否加没有更多了的FooterView
     */
    public void checkIfAddFooter(int dataSize) {
        if (dataSize < PAGE_SIZE) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.memu_action_download:
                if (TextUtils.isEmpty(mPinKey)) {
                    Snackbar.make(mCoordinator, "数据还未加载完毕，请稍后再试", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        AndPermission.with(this)
                                .requestCode(201)
                                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .rationale(mRationaleListener)
                                .send();
                    } else {
                        DownloadPinService.launch(ImageDetailActivity.this, mPinKey, mPinType);
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onGatherInfoDone(String gatherDes, int selection) {
        String toBoardId = mBoardIds[selection];

        Subscription s = RetrofitClient.createService(OperateApi.class)
                .httpGatherPinService(mAuthorization, toBoardId, gatherDes, String.valueOf(mPinId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatherResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSbtnlPin.setChecked(false);
                        checkException(e, mCoordinator);
                    }

                    @Override
                    public void onNext(GatherResultBean gatherResultBean) {
                        setUpWithGatherTv();
                        mIsGathered = true;
                        setUpPinSbtn();
                    }
                });
        addSubscription(s);
    }

    @Override
    public void onGatherCancel() {
        mSbtnlPin.setChecked(mIsGathered);
    }

    /**
     * 显示编辑对话框
     */
    private void showPinEditDialog() {

        PinEditDialogFragment fragment = PinEditDialogFragment.create(mAuthorization, String.valueOf(mPinId), mRawText, mCurrentBoardId);
        fragment.setListener(new PinEditDialogFragment.PinEditListener() {
            @Override
            public void onEditDone(String pinId, String des, String boardId) {
                httpForCommitEdit(pinId, des, boardId);
            }

            @Override
            public void onNeutralClicked(final String pinId) {
                //删除画板， 提示用户是否确定删除
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle(R.string.dialog_delete_attention_title)
                        .setMessage(R.string.dialog_pin_edit_delete_warning)
                        .setPositiveButton(R.string.dialog_title_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                httpForCommitDelete(pinId);
                            }
                        })
                        .setNegativeButton(R.string.dialog_negative, null);
                builder.create().show();
            }
        });
        fragment.show(getSupportFragmentManager(), null);

    }

    /**
     * 联网提交修改
     * @param pinId
     * @param des
     * @param boardId
     */
    private void httpForCommitEdit(String pinId, String des, String boardId) {

        Subscription s = RetrofitClient.createService(OperateApi.class)
                .httpEditPinService(mAuthorization, pinId, boardId, des)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PinsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mCoordinator, R.string.operate_request_failed, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PinsBean pinsBean) {
                        String tempBoardId = String.valueOf(pinsBean.getBoard_id());
                        if (!TextUtils.isEmpty(tempBoardId)) {
                            Snackbar.make(mCoordinator, R.string.edit_board_operate_success, Snackbar.LENGTH_SHORT).show();
                            ImageDetailActivity.launch(ImageDetailActivity.this, mPinId, mRatio, mImageDetail);
                            finishSelf();
                        }
                    }
                });
        addSubscription(s);
    }

    /**
     * 联网删除采集
     * @param pinId
     */
    private void httpForCommitDelete(String pinId) {

        Subscription s = RetrofitClient.createService(OperateApi.class)
                .httpDeletePinService(mAuthorization, pinId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mCoordinator, R.string.operate_request_failed, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Snackbar.make(mCoordinator, R.string.edit_board_operate_success, Snackbar.LENGTH_SHORT).show();
                        finishSelf();
                    }
                });
        addSubscription(s);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //获取权限成功的回调
    @PermissionYes(201)
    private void startDownload() {
        DownloadPinService.launch(ImageDetailActivity.this, mPinKey, mPinType);
    }

    //获取权限失败的回调
    @PermissionNo(201)
    private void cancelDownload() {
        Snackbar.make(mCoordinator, "获取访问文件权限失败，无法下载图片！", Snackbar.LENGTH_SHORT).show();
    }

    //请求权限被拒绝后提示
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(ImageDetailActivity.this)
                    .setTitle("友情提示")
                    .setMessage("没有访问文件权限将不能下载图片， 请把访问文件权限赐予给我吧！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rationale.cancel();
                        }
                    }).show();
        }
    };
}
