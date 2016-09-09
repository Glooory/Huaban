package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.entity.FollowerBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;

/**
 * Created by Glooory on 2016/9/7 0007 21:53.
 */
public class FollowerAdapter extends BaseQuickAdapter<FollowerBean.FollowersBean> {
    private Context mContext;
    private String mImgUrlRoot;
    private String mSmallSuffix;


    public FollowerAdapter(Context context) {
        super(R.layout.card_follower, null);
        this.mContext = context;
        mImgUrlRoot = mContext.getString(R.string.urlImageRoot);
        mSmallSuffix = mContext.getString(R.string.image_suffix_small);
    }

    @Override
    protected void convert(BaseViewHolder holder, FollowerBean.FollowersBean bean) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.checkIfNeedConvert(bean.getBoard_count()))
                .append(" 画板 ")
                .append(Utils.checkIfNeedConvert(bean.getPin_count()))
                .append("采集");

        holder.setText(R.id.tv_card_follower_name, bean.getUsername())
                .setText(R.id.tv_card_follower_counts, stringBuilder.toString())
                .addOnClickListener(R.id.card_follower);

        Drawable mProgressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        String key = bean.getAvatar().getKey();
        String avatarUrl = mImgUrlRoot + key + mSmallSuffix;
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_avatar), avatarUrl)
                .setIsCircle(true, true)
                .setProgressbarImage(mProgressDrawable)
                .build();

        if (bean.getPins() == null || bean.getPins().size() == 0) {
            holder.getView(R.id.ll_card_follower_tinys).setVisibility(View.GONE);
        }

        if (bean.getPins().size() > 0) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first)).setAspectRatio(1.0f);
            String keyF = bean.getPins().get(0).getFile().getKey();
            if (!TextUtils.isEmpty(keyF)) {
                String tinyFUrl = mImgUrlRoot + keyF + mSmallSuffix;
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first), tinyFUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

        if (bean.getPins().size() > 1) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second)).setAspectRatio(1.0f);
            String keyS = bean.getPins().get(1).getFile().getKey();
            if (!TextUtils.isEmpty(keyS)) {
                String tinySUrl = mImgUrlRoot + keyS + mSmallSuffix;
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second), tinySUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

        if (bean.getPins().size() > 2) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third)).setAspectRatio(1.0f);
            String keyT = bean.getPins().get(2).getFile().getKey();
            if (!TextUtils.isEmpty(keyT)) {
                String tinyTUrl = mImgUrlRoot + keyT + mSmallSuffix;
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third), tinyTUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

    }
}
