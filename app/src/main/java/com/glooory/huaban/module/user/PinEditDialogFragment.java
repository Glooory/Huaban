package com.glooory.huaban.module.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.glooory.huaban.R;
import com.glooory.huaban.api.UserApi;
import com.glooory.huaban.entity.LastBoardsBean;
import com.glooory.huaban.net.RetrofitClient;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.widget.HighLightArrayAdapter;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/9/25 0025 16:31.
 */

public class PinEditDialogFragment extends AppCompatDialogFragment {
    private static final String KEY_AUTH = "key_auth";
    private static final String KEY_PIN_ID = "key_pin_id";
    private static final String KEY_PIN_DES = "key_pin_des";
    private static final String KEY_PIN_BOARD_ID = "key_pin_board_id";

    private Context mContext;
    private String mAuthorization;
    private String mPinId;
    private String mPinDes;
    private String mBoardId;
    private String[] mBoardTitles;
    private String[] mBoardIds;
    private PinEditListener mListener;
    private HighLightArrayAdapter mAdapter;
    private int mSelection = 0;
    private CompositeSubscription mCompositeSubscription;

    private EditText mEditTextDes;
    private Spinner mSpinner;

    public static PinEditDialogFragment create(String authorization, String pinId, String pinDes, String boardId) {
        Bundle args = new Bundle();
        args.putString(KEY_AUTH, authorization);
        args.putString(KEY_PIN_ID, pinId);
        args.putString(KEY_PIN_DES, pinDes);
        args.putString(KEY_PIN_BOARD_ID, boardId);
        PinEditDialogFragment fragment = new PinEditDialogFragment();
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
        if (getArguments() != null) {
            mAuthorization = getArguments().getString(KEY_AUTH);
            mPinId = getArguments().getString(KEY_PIN_ID);
            mPinDes = getArguments().getString(KEY_PIN_DES);
            mBoardId = getArguments().getString(KEY_PIN_BOARD_ID);
        }
        mCompositeSubscription = new CompositeSubscription();
        httpForBoardsInfo();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_title_edit);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_pin_edit, null);
        initView(dialogView);
        builder.setView(dialogView);

        builder.setNegativeButton(R.string.dialog_negative, null);
        builder.setNeutralButton(R.string.dialog_pin_edit_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mListener != null) {
                    mListener.onNeutralClicked(mPinId);
                }
            }
        });

        builder.setPositiveButton(R.string.dialog_edit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mListener != null) {
                    mListener.onEditDone(mPinId,
                            mEditTextDes.getText().toString(),
                            mBoardIds[mSelection]);
                }
            }
        });
        return builder.create();
    }

    private void initView(View dialogView) {
        mEditTextDes = (EditText) dialogView.findViewById(R.id.edit_dialog_pin_edit_des);
        mSpinner = (Spinner) dialogView.findViewById(R.id.spinner_pin_edit);
    }

    private void initAdapter() {

        mEditTextDes.setText(mPinDes);

        mAdapter = new HighLightArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setSelection(mSelection);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelection = position;
                mAdapter.setSelection(mSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取用户的所有画板信息，如果失败则从本地取
     */
    private void httpForBoardsInfo() {

        Subscription s = RetrofitClient.createService(UserApi.class)
                .httpsBoardListInfo(mAuthorization, Constant.OPERATEBOARDEXTRA)
                .map(new Func1<LastBoardsBean, List<LastBoardsBean.BoardsBean>>() {
                    @Override
                    public List<LastBoardsBean.BoardsBean> call(LastBoardsBean lastBoardsBean) {
                        return lastBoardsBean.getBoards();
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
                        String boardTitleArray = (String) SPUtils.get(mContext.getApplicationContext(), Constant.BOARDTITLEARRAY, "");
                        String mBoardId = (String) SPUtils.get(mContext.getApplicationContext(), Constant.BOARDIDARRAY, "");

                        mBoardTitles = boardTitleArray != null ? boardTitleArray.split(Constant.SEPARATECOMMA) : new String[0];
                        mBoardIds = mBoardId != null ? mBoardId.split(Constant.SEPARATECOMMA) : new String[0];
                        initAdapter();
                    }

                    @Override
                    public void onNext(List<LastBoardsBean.BoardsBean> boardsBeen) {
                        saveBoardInfo(boardsBeen);
                    }
                });
        mCompositeSubscription.add(s);
    }

    /**
     * 保存所有画板信息
     *
     * @param bean
     */
    private void saveBoardInfo(List<LastBoardsBean.BoardsBean> bean) {

        if (bean != null) {
            mBoardTitles = new String[bean.size()];
            mBoardIds = new String[bean.size()];
            for (int i = 0; i < bean.size(); i++) {
                mBoardTitles[i] = bean.get(i).getTitle();
                mBoardIds[i] = String.valueOf(bean.get(i).getBoard_id());
                if (mBoardId.equals(mBoardIds[i])) {
                    mSelection = i;
                }
            }
        }

    }

    public void setListener(PinEditListener listener) {
        this.mListener = listener;
    }

    public interface PinEditListener {

        void onEditDone(String pinId, String des, String boardId);

        void onNeutralClicked(String pinId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
