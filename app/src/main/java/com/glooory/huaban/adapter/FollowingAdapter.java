package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.module.user.UserFollowingBean;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;

/**
 * Created by Glooory on 2016/9/15 0015 12:29.
 */
public class FollowingAdapter extends BaseQuickAdapter<UserFollowingBean.UsersBean> {
    private Context mContext;
    private String mSmallImgUrl;


    public FollowingAdapter(Context context) {
        super(R.layout.card_follower, null);
        this.mContext = context;
        mSmallImgUrl = mContext.getString(R.string.format_url_image_small);
    }

    @Override
    protected void convert(BaseViewHolder holder, UserFollowingBean.UsersBean bean) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.checkIfNeedConvert(bean.getBoard_count()))
                .append(" 画板 ")
                .append(Utils.checkIfNeedConvert(bean.getPin_count()))
                .append("采集");

        holder.setText(R.id.tv_card_follower_name, bean.getUsername())
                .setText(R.id.tv_card_follower_counts, stringBuilder.toString())
                .addOnClickListener(R.id.card_follower_ripple);

        Drawable mProgressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        String avatarUrl = String.format(mSmallImgUrl, bean.getAvatar().getKey());
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_avatar), avatarUrl)
                .setIsCircle(true, true)
                .setProgressbarImage(mProgressDrawable)
                .build();
        ((SimpleDraweeView) holder.getView(R.id.img_card_follower_avatar)).setVisibility(View.VISIBLE);

        if (bean.getPins() == null || bean.getPins().size() == 0) {
            holder.getView(R.id.ll_card_follower_tinys).setVisibility(View.GONE);
        }

        if (bean.getPins().size() > 0) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first)).setAspectRatio(1.0f);
            String keyF = bean.getPins().get(0).getFile().getKey();
            if (!TextUtils.isEmpty(keyF)) {
                String tinyFUrl = String.format(mSmallImgUrl, keyF);
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first), tinyFUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

        if (bean.getPins().size() > 1) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second)).setAspectRatio(1.0f);
            String keyS = bean.getPins().get(1).getFile().getKey();
            if (!TextUtils.isEmpty(keyS)) {
                String tinySUrl = String.format(mSmallImgUrl, keyS);
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second), tinySUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

        if (bean.getPins().size() > 2) {
            ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third)).setAspectRatio(1.0f);
            String keyT = bean.getPins().get(2).getFile().getKey();
            if (!TextUtils.isEmpty(keyT)) {
                String tinyTUrl = String.format(mSmallImgUrl, keyT);
                new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third), tinyTUrl)
                        .setIsRadius(true, 8)
                        .build();
            }
        }

    }
}
