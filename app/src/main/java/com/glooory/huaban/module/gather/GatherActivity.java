package com.glooory.huaban.module.gather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dd.CircularProgressButton;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.glooory.huaban.R;
import com.glooory.huaban.api.UploadApi;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.entity.LastBoardsBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.widget.HighLightArrayAdapter;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.glooory.huaban.R.string.upload_failed;

/**
 * Created by Glooory on 2016/9/23 0023 11:48.
 */

public class GatherActivity extends BaseActivity {
    private String mImgPath;
    private String[] mBoardTitles = new String[]{};
    private String[] mBoardIds = new String[]{};
    private int mSelection = 0;
    private String mFileId;
    private String mBoardId;
    private RxBusResultSubscriber<ImageRadioResultEvent> mResultSubscriber;


    @BindView(R.id.btn_upload)
    CircularProgressButton mBtnUpload;
    @BindView(R.id.toolbar_gather)
    Toolbar mToolbar;
    @BindView(R.id.img_gather)
    SimpleDraweeView mImgPreview;
    @BindView(R.id.spinner_gather)
    Spinner mSpinner;
    @BindView(R.id.edittext_gather_des)
    EditText mEdittextGatherDes;
    @BindView(R.id.btn_cancel)
    CircularProgressButton mBtnCancel;
    @BindView(R.id.btn_gather)
    CircularProgressButton mBtnGather;
    @BindView(R.id.ll_gather)
    LinearLayout mLiGather;
    @BindView(R.id.coordinator_gather)
    CoordinatorLayout mCoordinator;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, GatherActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gather;
    }

    @Override
    protected String getTAG() {
        return "GatherActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        httpForBoards();
    }

    private void initView() {

        mLiGather.setVisibility(View.INVISIBLE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.gather_new_one);

        mBtnUpload.setIndeterminateProgressMode(true);
        mBtnGather.setIndeterminateProgressMode(true);

        mResultSubscriber = new RxBusResultSubscriber<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent resultEvent) throws Exception {
                actionUpload(resultEvent);
            }
        };

        RxView.clicks(mBtnUpload)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxGalleryFinal
                                .with(mContext.getApplicationContext())
                                .image()
                                .radio()
                                .crop()
                                .imageLoader(ImageLoaderType.FRESCO)
                                .subscribe(mResultSubscriber)
                                .openGallery();
                    }
                });

        RxView.clicks(mBtnGather)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        acitonGather(mFileId);
                    }
                });


        RxView.clicks(mBtnCancel)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        finishSelf();
                    }
                });
    }

    /**
     * 联网请求用户的所有画板
     */
    private void httpForBoards() {

        Subscription s = RetrofitClient.createService(UserApi.class)
                .httpsBoardListInfo(mAuthorization, Constant.OPERATEBOARDEXTRA)
                .map(new Func1<LastBoardsBean, List<LastBoardsBean.BoardsBean>>() {
                    @Override
                    public List<LastBoardsBean.BoardsBean> call(LastBoardsBean lastBoardsBean) {
                        return lastBoardsBean.getBoards();
                    }
                })
                .filter(new Func1<List<LastBoardsBean.BoardsBean>, Boolean>() {
                    @Override
                    public Boolean call(List<LastBoardsBean.BoardsBean> boardsBeen) {
                        return boardsBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LastBoardsBean.BoardsBean>>() {
                    @Override
                    public void onCompleted() {
                        initAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Logger.d(e.getMessage());
                        String boardTitleArray = (String) SPUtils.get(getApplicationContext(), Constant.BOARDTITLEARRAY, "");
                        String mBoardId = (String) SPUtils.get(getApplicationContext(), Constant.BOARDIDARRAY, "");

                        mBoardTitles = boardTitleArray != null ? boardTitleArray.split(Constant.SEPARATECOMMA) : new String[0];
                        mBoardIds = mBoardId != null ? mBoardId.split(Constant.SEPARATECOMMA) : new String[0];
                        initAdapter();
                    }

                    @Override
                    public void onNext(List<LastBoardsBean.BoardsBean> bean) {
                        if (bean != null) {
                            mBoardTitles = new String[bean.size()];
                            mBoardIds = new String[bean.size()];
                            for (int i = 0; i < bean.size(); i++) {
                                mBoardTitles[i] = bean.get(i).getTitle();
                                mBoardIds[i] = String.valueOf(bean.get(i).getBoard_id());
                            }
                        }
                    }
                });
        addSubscription(s);
    }

    private void initAdapter() {
        final HighLightArrayAdapter adapter = new HighLightArrayAdapter(
                mContext, R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinner.setAdapter(adapter);
        adapter.setSelection(mSelection);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelection = position;
                adapter.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 上传所选择的图片到服务器
     * @param resultEvent
     */
    private void actionUpload(ImageRadioResultEvent resultEvent) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLiGather.setVisibility(View.VISIBLE);
                mBtnUpload.setProgress(1);
                mBtnGather.setEnabled(false);
            }
        });

        //获取图片的长和宽，适当压缩图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resultEvent.getResult().getOriginalPath(), options);
        float ratio = options.outWidth / (float) options.outHeight;
        int desireWidth = mScreenWidthPixels / 2;
        int desireHeight = options.outHeight;
        if (options.outWidth > desireWidth) {
            desireHeight = (int) (desireWidth / ratio);
        } else {
            desireWidth = options.outWidth;
        }

        new FrescoLoader.Builder(mContext,
                mImgPreview,
                "file://" + resultEvent.getResult().getOriginalPath())
                .setResizeOptions(new ResizeOptions(desireWidth, desireHeight))
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build();


        final File file = new File(resultEvent.getResult().getOriginalPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Subscription s = RetrofitClient.createService(UploadApi.class)
                .httpUploadImgService(mAuthorization, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UploadResultBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkException(e, mCoordinator);
                        mBtnUpload.setProgress(0);
                    }

                    @Override
                    public void onNext(UploadResultBean uploadResultBean) {
                        mBtnUpload.setProgress(0);
                        if (uploadResultBean.getMsg() == null) {
                            mFileId = String.valueOf(uploadResultBean.getId());
                            mBtnGather.setEnabled(true);
                        } else {
                            showSnackbarMsg(upload_failed, false);
                        }
                    }
                });
        addSubscription(s);
    }

    /**
     * 将刚才上传的图片采集到自己的画板中
     * @param fileId
     */
    private void acitonGather(final String fileId) {

        if (fileId == null || TextUtils.isEmpty(fileId)) {
            return;
        }
        mBtnGather.setProgress(1);
        Subscription s = RetrofitClient.createService(UploadApi.class)
                .httpGatherService(mAuthorization, mBoardIds[mSelection], mEdittextGatherDes.getText().toString(),
                        fileId, 1, true, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatherResultBean>() {
                    @Override
                    public void onCompleted() {
                        mBtnGather.setProgress(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBtnGather.setProgress(0);
                        checkException(e, mCoordinator);
                    }

                    @Override
                    public void onNext(GatherResultBean gatherResultBean) {
                        if (gatherResultBean.getPin() != null) {
                            float ratio = gatherResultBean.getPin().getFile().getWidth() /
                                    (float) gatherResultBean.getPin().getFile().getHeight();
                            showSuccessSnackbar(gatherResultBean.getPin().getPin_id(), ratio);
                        } else {
                            showSnackbarMsg(R.string.gather_failed, false);
                        }
                    }
                });
        addSubscription(s);
    }

    private void showSuccessSnackbar(final int pinId, final float ratio) {

        Snackbar snackbar = Snackbar.make(mCoordinator,
                String.format(getString(R.string.gather_success_toast), mBoardTitles[mSelection]),
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.checkout_board, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDetailActivity.launch(GatherActivity.this,
                        pinId,
                        ratio,
                        mImgPreview);
            }
        });
        snackbar.show();

    }

    private void showSnackbarMsg(int msgId, boolean shortDuration) {
        Snackbar.make(mCoordinator,
                msgId,
                shortDuration ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mResultSubscriber.isUnsubscribed()) {
            Logger.d(String.valueOf(mResultSubscriber.isUnsubscribed()));
            mResultSubscriber.unsubscribe();
        }
    }
}
