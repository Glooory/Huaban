package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.net.FrescoLoader;
import com.glooory.huaban.entity.type.TypeUserListBean;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;

/**
 * Created by Glooory on 2016/9/17 0017 16:52.
 */
public class TypeUserAdapter extends BaseQuickAdapter<TypeUserListBean.PusersBean> {
    private Context mContext;
    private String mSmallImgUrl;

    public TypeUserAdapter(Context context) {
        super(R.layout.card_follower, null);
        this.mContext = context;
        mSmallImgUrl = mContext.getString(R.string.format_url_image_small);
    }

    @Override
    protected void convert(BaseViewHolder holder, TypeUserListBean.PusersBean bean) {

        int followText = bean.getUser().getFollowing() == 1 ? R.string.follow_action_unfollow :
        R.string.follow_action_follow;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.checkIfNeedConvert(bean.getUser().getBoard_count()))
                .append(" 画板 ")
                .append(Utils.checkIfNeedConvert(bean.getUser().getPin_count()))
                .append("采集");

        holder.setText(R.id.tv_card_follower_name, bean.getUser().getUsername())
                .setText(R.id.tv_card_follower_counts, stringBuilder.toString())
                .setText(R.id.tv_card_user_follow_operate, followText)
                .addOnClickListener(R.id.card_follower_ripple)
                .addOnClickListener(R.id.tv_card_user_follow_operate);

        Drawable mProgressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        String avatarUrl = String.format(mSmallImgUrl, bean.getUser().getAvatar().getKey());
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_avatar), avatarUrl)
                .setIsCircle(true, true)
                .setProgressbarImage(mProgressDrawable)
                .build();
        ((SimpleDraweeView) holder.getView(R.id.img_card_follower_avatar)).setVisibility(View.VISIBLE);

        if (bean.getUser().getBoards() == null || bean.getUser().getBoards().size() == 0) {
            holder.getView(R.id.ll_card_follower_tinys).setVisibility(View.GONE);
        }

        if (bean.getUser().getBoards().size() > 0) {
            if (bean.getUser().getBoards().get(0).getPins().size() > 0) {
                ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first)).setAspectRatio(1.0f);
                String keyF = bean.getUser().getBoards().get(0).getPins().get(0).getFile().getKey();
                if (!TextUtils.isEmpty(keyF)) {
                    String tinyFUrl = String.format(mSmallImgUrl, keyF);
                    new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_first), tinyFUrl)
                            .setIsRadius(true, 8)
                            .build();
                }
            }
        }

        if (bean.getUser().getBoards().size() > 1) {
            if (bean.getUser().getBoards().get(1).getPins().size() > 0) {
                ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second)).setAspectRatio(1.0f);
                String keyS = bean.getUser().getBoards().get(1).getPins().get(0).getFile().getKey();
                if (!TextUtils.isEmpty(keyS)) {
                    String tinySUrl = String.format(mSmallImgUrl, keyS);
                    new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_second), tinySUrl)
                            .setIsRadius(true, 8)
                            .build();
                }
            }
        }

        if (bean.getUser().getBoards().size() > 2) {
            if (bean.getUser().getBoards().get(2).getPins().size() > 0) {
                ((SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third)).setAspectRatio(1.0f);
                String keyT = bean.getUser().getBoards().get(2).getPins().get(0).getFile().getKey();
                if (!TextUtils.isEmpty(keyT)) {
                    String tinyTUrl = String.format(mSmallImgUrl, keyT);
                    new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_card_follower_tiny_third), tinyTUrl)
                            .setIsRadius(true, 8)
                            .build();
                }
            }
        }

    }

}
