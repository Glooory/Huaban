package com.glooory.huaban.module.user;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.glooory.huaban.R;
import com.glooory.huaban.widget.HighLightArrayAdapter;

import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/14 0014 21:57.
 */
public class BoardEditDialogFragment extends AppCompatDialogFragment {
    private static final String KEY_BOARD_ID = "key_board_id";
    private static final String KEY_BOARD_NAME = "key_board_name";
    private static final String KEY_BOARD_DES = "key_board_des";
    private static final String KEY_BOARD_TYPE = "key_board_type";

    private EditText mEditTextBoardName;
    private EditText mEditTextBoardDes;
    private Spinner mSpinnerTitles;

    private Context mContext;

    private String mBoardIdIn;
    private String mBoardNameIn;
    private String mBoardDesIn;
    private String mBoardTypeIn;

    private String[] titles;
    private boolean isChanged = false;
    private BoardEditListener mListener;

    public static BoardEditDialogFragment create(String boardId, String name, String des, String type) {
        Bundle args = new Bundle();
        args.putString(KEY_BOARD_ID, boardId);
        args.putString(KEY_BOARD_NAME, name);
        args.putString(KEY_BOARD_DES, des);
        args.putString(KEY_BOARD_TYPE, type);
        BoardEditDialogFragment fragment = new BoardEditDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardIdIn = getArguments().getString(KEY_BOARD_ID);
            mBoardNameIn = getArguments().getString(KEY_BOARD_NAME);
            mBoardDesIn = getArguments().getString(KEY_BOARD_DES);
            mBoardTypeIn = getArguments().getString(KEY_BOARD_TYPE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_title_edit);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_board_edit, null);
        initView(dialogView);
        setData();
        builder.setView(dialogView);

        builder.setNegativeButton(R.string.dialog_negative, null);
        builder.setNeutralButton(R.string.dialog_delete_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mListener != null) {
                    mListener.onNeutralClicked(mBoardIdIn);
                }
            }
        });

        builder.setPositiveButton(R.string.dialog_edit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checkDataIsChanged()) {
                    if (mListener != null) {
                        mListener.onEditDone(mBoardIdIn,
                                mEditTextBoardName.getText().toString().trim(),
                                mEditTextBoardDes.getText().toString().trim(),
                                mBoardTypeIn);
                    }
                }
            }
        });
        return builder.create();
    }

    private void initView(View view) {
        mEditTextBoardName = ButterKnife.findById(view, R.id.edit_board_edit_name);
        mEditTextBoardDes = ButterKnife.findById(view, R.id.edit_board_edit_des);
        mSpinnerTitles = ButterKnife.findById(view, R.id.spinner_board_edit);
    }

    private void setData() {

        //画板名称
        if (!TextUtils.isEmpty(mBoardNameIn)) {
            mEditTextBoardName.setText(mBoardNameIn);
        } else {
            mEditTextBoardName.setText(R.string.text_is_default);
        }

        //画板描述， 可以为空
        mEditTextBoardDes.setText(mBoardDesIn);

        //画板类型
        titles = getResources().getStringArray(R.array.type_names);
        final String[] types = getResources().getStringArray(R.array.type_value);
        int selection = 0;

        if (mBoardTypeIn != null) {
            for (int i = 0; i < titles.length; i++) {
                if (types[i].equals(mBoardTypeIn)) {
                    selection = i;
                }
            }
        }

        final HighLightArrayAdapter adapter = new HighLightArrayAdapter(mContext,
                R.layout.support_simple_spinner_dropdown_item, titles);
        mSpinnerTitles.setAdapter(adapter);
        mSpinnerTitles.setSelection(selection);
        adapter.setSelection(selection);
        mSpinnerTitles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelection(i);
                String selectedType = types[i];
                if (!selectedType.equals(mBoardTypeIn)) {
                    mBoardTypeIn = types[i];
                    isChanged = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean checkDataIsChanged() {

        boolean isChanged = this.isChanged;
        if (isChanged) {
            return true;
        }

        String input;
        input = mEditTextBoardName.getText().toString().trim();
        if ((!TextUtils.isEmpty(input)) && (!input.equals(mBoardNameIn))) {
            return true;
        }

        input = mEditTextBoardDes.getText().toString().trim();
        if ((!TextUtils.isEmpty(input)) && (!input.equals(mBoardDesIn))) {
            return true;
        }

        return false;

    }

    public void setListener(BoardEditListener listener) {
        this.mListener = listener;
    }

    /**
     * 对话框点击事件的回调
     */
    public interface BoardEditListener {

        void onEditDone(String boardId, String name, String des, String type);

        void onNeutralClicked(String boardId);

    }

}
