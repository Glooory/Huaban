package com.glooory.huaban.module.imagedetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.glooory.huaban.R;
import com.glooory.huaban.base.BaseActivity;
import com.sackcentury.shinebuttonlib.ShineButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/10 0010 19:43.
 */
public class ImageDetailActivity extends BaseActivity {


    @BindView(R.id.sbtn_image_detail_pin)
    ShineButton mSbtnlPin;
    @BindView(R.id.ll_iamge_detail_pin)
    LinearLayout mPin;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_image_detail_header;
    }

    @Override
    protected String getTAG() {
        return "ImageDetailActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        mPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSbtnlPin.performClick();
            }
        });
        mSbtnlPin.setChecked(true);
    }


}
