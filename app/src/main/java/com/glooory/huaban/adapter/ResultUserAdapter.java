package com.glooory.huaban.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.entity.PinsUserBean;
import com.glooory.huaban.httputils.FrescoLoader;

/**
 * Created by Glooory on 2016/9/18 0018 18:38.
 */
public class ResultUserAdapter extends BaseQuickAdapter<PinsUserBean> {
    private Context mContext;
    private String mSmallFormat;
    private String mFollowerFormat;

    public ResultUserAdapter(Context context) {
        super(R.layout.card_result_user, null);
        mContext = context;
        mSmallFormat = context.getResources().getString(R.string.format_url_image_small);
        mFollowerFormat = context.getResources().getString(R.string.format_fans_number);
    }

    @Override
    protected void convert(BaseViewHolder holder, PinsUserBean bean) {
        holder.setText(R.id.tv_card_result_follower_count, String.format(mFollowerFormat, bean.getFollower_count()))
                .setText(R.id.tv_card_result_user_name, bean.getUsername())
                .addOnClickListener(R.id.card_result_user);

        ((SimpleDraweeView) holder.getView(R.id.img_card_result_user_avatar)).setAspectRatio(1.0f);
        ((SimpleDraweeView) holder.getView(R.id.img_card_result_user_avatar)).setVisibility(View.VISIBLE);
        new FrescoLoader.Builder(mContext,
                (SimpleDraweeView) holder.getView(R.id.img_card_result_user_avatar),
                String.format(mSmallFormat, bean.getAvatar().getKey()))
                .setIsCircle(true)
                .build();

    }
}
