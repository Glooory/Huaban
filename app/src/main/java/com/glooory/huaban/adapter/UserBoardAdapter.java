package com.glooory.huaban.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.module.user.UserBoardItemBean;
import com.glooory.huaban.util.CompatUtils;

/**
 * Created by Glooory on 2016/9/3 0003 19:15.
 */
public class UserBoardAdapter extends BaseQuickAdapter<UserBoardItemBean> {
    private boolean isMe;//是否是自己， ture为是 false 为其他人

    private final String mAttentionFormat; //画板的关注数量
    private final String mGatherFormat; // 画板的采集数量
    private final String mOperateEdit; // 编辑操作
    private final String mOperateFollowing; //关注
    private final String mOperateFollowed; //已关注

    private final Drawable mDrawableBlock;
    private final Drawable mDrawableEdit;
    private final Drawable mDrawableFollowing;
    private final Drawable mDrawableFollowed;

    private Context mContext;
    private String img_root_url;
    private String img_suffix_small;


    public UserBoardAdapter(Context context, boolean isMe) {
        super(R.layout.cardview_item_board_user, null);
        mContext = context;
        this.isMe = isMe;
        Resources resources = mContext.getResources();
        img_root_url = resources.getString(R.string.urlImageRoot);
        img_suffix_small = resources.getString(R.string.image_suffix_small);
        this.mGatherFormat = resources.getString(R.string.text_gather_number);
        this.mAttentionFormat = resources.getString(R.string.text_attention_number);
        this.mOperateEdit = resources.getString(R.string.text_edit);
        this.mOperateFollowing = resources.getString(R.string.text_following);
        this.mOperateFollowed = resources.getString(R.string.text_followed);
        this.mDrawableBlock = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_block_black_24dp, R.color.tint_list_grey);
        this.mDrawableEdit = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_mode_edit_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowing = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_add_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowed = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_check_black_24dp, R.color.tint_list_grey);

    }

    @Override
    protected void convert(BaseViewHolder holder, UserBoardItemBean bean) {
        boolean isOpreat = false;
        //不为0的标志位才能操作
        if (bean.getDeleting() != 0) {
            isOpreat = true;
        }

        boolean isFollowing = bean.isFollowing();
        Drawable drawable;
        String text;

        if (isOpreat) {
            if (isMe) {
                text = mOperateEdit;
                drawable = mDrawableEdit;
            } else {
                if (isFollowing) {
                    text = mOperateFollowed;
                    drawable = mDrawableFollowed;
                } else {
                    text = mOperateFollowing;
                    drawable = mDrawableFollowing;
                }
            }
        } else {
            text = "";
            drawable = mDrawableBlock;
        }

        holder.setText(R.id.tv_board_operate, text);
        holder.getView(R.id.tv_board_operate).setTag(isOpreat);

        ((TextView) holder.getView(R.id.tv_board_operate)).setCompoundDrawablesWithIntrinsicBounds(
                drawable, null, null, null
        );

        holder.setText(R.id.tv_board_title, bean.getTitle());
        holder.setText(R.id.tv_board_gather, String.format(mGatherFormat, bean.getPin_count()));
        holder.setText(R.id.tv_board_attention, String.format(mAttentionFormat, bean.getFollow_count()));

        holder.addOnClickListener(R.id.linearlayout_image)
                .addOnClickListener(R.id.tv_board_operate);

        String url_img = img_root_url + getFirstPinsFileKey(bean);

        float ratio = 1.0f;
        ((SimpleDraweeView) holder.getView(R.id.img_card_image)).setAspectRatio(ratio);

        Drawable dProgressImg =
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);

        new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_card_image)), url_img)
                .setProgressbarImage(dProgressImg)
                .build();

        int boardPinsCount = bean.getPins().size();

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_first)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 1) {
            String firstImgUrl = img_root_url + bean.getPins().get(1).getFile().getKey() + img_suffix_small;
            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_first)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 2) {
            String firstImgUrl = img_root_url + bean.getPins().get(2).getFile().getKey() + img_suffix_small;

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 3) {
            String firstImgUrl = img_root_url + bean.getPins().get(3).getFile().getKey() + img_suffix_small;

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_fourth)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 4) {
            String firstImgUrl = img_root_url + bean.getPins().get(4).getFile().getKey() + img_suffix_small;

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_fourth)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }
    }

    private String getFirstPinsFileKey(UserBoardItemBean bean) {
        int size = bean.getPins().size();
        if (size > 0) {
            return bean.getPins().get(0).getFile().getKey();
        } else {
            return "";
        }
    }
}
