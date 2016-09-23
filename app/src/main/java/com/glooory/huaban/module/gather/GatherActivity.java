package com.glooory.huaban.module.gather;

import android.app.Activity;
import android.content.Intent;
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
import com.glooory.huaban.R;
import com.glooory.huaban.api.UploadApi;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.entity.BoardItemInfoBean;
import com.glooory.huaban.entity.BoardListInfoBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.imagedetail.ImageDetailActivity;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.widget.HighLightArrayAdapter;
import com.jakewharton.rxbinding.view.RxView;

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
                                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                                    @Override
                                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                        //图片选择结果
                                        actionUpload(imageRadioResultEvent);
                                        mLiGather.setVisibility(View.VISIBLE);
                                    }
                                })
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
                .map(new Func1<BoardListInfoBean, List<BoardItemInfoBean>>() {
                    @Override
                    public List<BoardItemInfoBean> call(BoardListInfoBean boardListInfoBean) {
                        return boardListInfoBean.getBoards();
                    }
                })
                .filter(new Func1<List<BoardItemInfoBean>, Boolean>() {
                    @Override
                    public Boolean call(List<BoardItemInfoBean> boardItemInfoBeen) {
                        return boardItemInfoBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BoardItemInfoBean>>() {
                    @Override
                    public void onCompleted() {
                        initAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String boardTitleArray = (String) SPUtils.get(getApplicationContext(), Constant.BOARDTITLEARRAY, "");
                        String mBoardId = (String) SPUtils.get(getApplicationContext(), Constant.BOARDIDARRAY, "");

                        mBoardTitles = boardTitleArray != null ? boardTitleArray.split(Constant.SEPARATECOMMA) : new String[0];
                        mBoardIds = mBoardId != null ? mBoardId.split(Constant.SEPARATECOMMA) : new String[0];
                        initAdapter();
                    }

                    @Override
                    public void onNext(List<BoardItemInfoBean> boardItemInfoBeen) {
                        if (boardItemInfoBeen != null) {
                            for (int i = 0; i < boardItemInfoBeen.size(); i++) {
                                mBoardTitles[i] = boardItemInfoBeen.get(i).getTitle();
                                mBoardIds[i] = String.valueOf(boardItemInfoBeen.get(i).getBoard_id());
                            }
                        }
                    }
                });
        addSubscription(s);
    }

    private void initAdapter() {
        final HighLightArrayAdapter adapter = new HighLightArrayAdapter(
                mContext, android.R.layout.simple_dropdown_item_1line, mBoardTitles);
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
                mBtnUpload.setProgress(1);
            }
        });

        new FrescoLoader.Builder(mContext,
                mImgPreview,
                "file://" + resultEvent.getResult().getOriginalPath())
//                                                .setResizeOptions(new ResizeOptions(mScreenWidthPixels / 2, desireHeight))
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
                        mBtnGather.setEnabled(false);
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
                            mBtnGather.setEnabled(false);
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
}
