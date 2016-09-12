package com.glooory.huaban.module.imagedetail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.glooory.huaban.R;
import com.glooory.huaban.api.OperateApi;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.util.Constant;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/12 0012 18:51.
 */
public class GatherDiologFragment extends AppCompatDialogFragment {
    private static final String KEY_AUTHORIZATION = "key_authorization";
    private static final String KEY_VIAID = "key_viaid";
    private static final String KEY_DES = "key_des";
    private static final String KEY_TITLES = "key_titles";

    private Context mContext;
    private String mAuthorization;
    private int mViaId;
    private String mDes;
    private String[] mBoardTitles;
    private int mSelection = 0;//默认选中项
    private GatherInfoListener mListener;

    private EditText mEditTextDes;
    private TextView mTvWarning;
    private Spinner mSpinner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ImageDetailActivity) {
            mListener = (GatherInfoListener) context;
        }
    }

    public static GatherDiologFragment create(String authorization, int viaId, String des, String[] boardTitles) {
        Bundle args = new Bundle();
        args.putString(KEY_AUTHORIZATION, authorization);
        args.putInt(KEY_VIAID, viaId);
        args.putString(KEY_DES, des);
        args.putStringArray(KEY_TITLES, boardTitles);
        GatherDiologFragment fragment = new GatherDiologFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuthorization = getArguments().getString(KEY_AUTHORIZATION);
            mViaId = getArguments().getInt(KEY_VIAID);
            mDes = getArguments().getString(KEY_DES);
            mBoardTitles = getArguments().getStringArray(KEY_TITLES);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.dialog_title_gather));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogContent = inflater.inflate(R.layout.dialog_gather, null);
        initView(dialogContent);
        builder.setView(dialogContent);

        builder.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mListener != null) {
                    mListener.onGatherCancel();
                }
            }
        });
        builder.setPositiveButton(R.string.dialog_gather_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input = mEditTextDes.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    input = mEditTextDes.getHint().toString();
                }
                mListener.onGatherInfoDone(input, mSelection);
            }
        });

        httpForIsGathered();

        return builder.create();


    }

    private void initView(View dialogView) {

        mEditTextDes = ButterKnife.findById(dialogView, R.id.edit_gather_dialog_des);
        mTvWarning = ButterKnife.findById(dialogView, R.id.tv_gather_dialog_warning);
        mSpinner = ButterKnife.findById(dialogView, R.id.spinner_gather_dialog);

        if (TextUtils.isEmpty(mDes)) {
            mEditTextDes.setHint(R.string.text_image_describe_null);
        } else {
            mEditTextDes.setHint(mDes);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelection = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //检查图片是否已经采集过
    private void httpForIsGathered() {

        new RetrofitClient().createService(OperateApi.class)
                .httpGatheredInfoService(mAuthorization, mViaId, Constant.OPERATECHECK)
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
                            String formatWarning = mContext.getString(R.string.text_gather_warning);
                            mTvWarning.setText(String.format(formatWarning, gatherInfoBean.getExist_pin().getBoard().getTitle()));
                            mTvWarning.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    public static interface GatherInfoListener {
        void onGatherInfoDone(String gatherDes, int selection);

        void onGatherCancel();
    }

}
