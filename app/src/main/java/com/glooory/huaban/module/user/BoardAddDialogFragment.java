package com.glooory.huaban.module.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
 * Created by Glooory on 2016/9/14 0014 18:49.
 */
public class BoardAddDialogFragment extends AppCompatDialogFragment {

    private EditText mEditTextBoardName;
    private EditText mEditTextBoardDes;
    private Spinner mSpinnerBoardType;

    private Context mContext;
    private String mBoardName;
    private String mBoardType;
    private String[] titles;
    private boolean isChange = false; //输入值是否有变化\
    private BoardAddListener mListener;

    public static BoardAddDialogFragment create() {

        return new BoardAddDialogFragment();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.dialog_title_add));
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_board_edit, null);
        initView(dialogView);
        setUpData();
        builder.setView(dialogView);
        builder.setNegativeButton(R.string.dialog_negative, null);
        builder.setPositiveButton(R.string.dialog_add_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isDataChanged()) {
                    String boardName = mEditTextBoardName.getText().toString();
                    if (TextUtils.isEmpty(boardName)) {
                        boardName = mEditTextBoardName.getHint().toString();
                    }
                    mListener.onEditDone(boardName, mEditTextBoardDes.getText().toString(), mBoardType);
                }
            }
        });
        return builder.create();
    }

    private void initView(View view) {
        mEditTextBoardName = ButterKnife.findById(view, R.id.edit_board_edit_name);
        mEditTextBoardDes = ButterKnife.findById(view, R.id.edit_board_edit_des);
        mSpinnerBoardType = ButterKnife.findById(view, R.id.spinner_board_edit);
    }

    private void setUpData() {

        mEditTextBoardName.setHint(R.string.text_is_default);
        mBoardName = mEditTextBoardName.getHint().toString();

        titles = getResources().getStringArray(R.array.type_names);
        final String[] types = getResources().getStringArray(R.array.type_value);
        final int selection = 0;

        final HighLightArrayAdapter adapter =
                new HighLightArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, titles);
        mSpinnerBoardType.setAdapter(adapter);
        mSpinnerBoardType.setSelection(selection);
        adapter.setSelection(selection);
        mSpinnerBoardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinnerBoardType.setSelection(i);
                adapter.setSelection(i);
                String selected = types[i];
                if (!selected.equals(mBoardType)) {
                    mBoardType = types[i];
                    isChange = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isDataChanged() {

        boolean isChanged = this.isChange;
        if (isChanged) {
            return true;
        }

        String input;
        input = mEditTextBoardName.getText().toString();
        if (!TextUtils.isEmpty(input) && (!input.equals(mBoardName))) {
            return true;
        }

        return false;

    }

    public void setListener(BoardAddListener listener) {
        this.mListener = listener;
    }

    public interface BoardAddListener {

        public abstract void onEditDone(String name, String des, String type);

    }

}
