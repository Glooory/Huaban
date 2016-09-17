package com.glooory.huaban.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.glooory.huaban.R;
import com.glooory.huaban.base.BaseActivity;
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
    private String mGeneralImgUrl;
    private String mSmallImgUrl;
    private int mDesireWidth;
    private ResizeOptions mResizeOptions;

    public UserBoardAdapter(Context context, boolean isMe) {
        super(R.layout.cardview_item_board_user, null);
        mContext = context;
        this.isMe = isMe;
        Resources resources = mContext.getResources();
        mGeneralImgUrl = resources.getString(R.string.format_url_image_general);
        mSmallImgUrl = resources.getString(R.string.format_url_image_small);
        this.mGatherFormat = resources.getString(R.string.format_gather_number);
        this.mAttentionFormat = resources.getString(R.string.format_attention_number);
        this.mOperateEdit = resources.getString(R.string.text_edit);
        this.mOperateFollowing = resources.getString(R.string.text_following);
        this.mOperateFollowed = resources.getString(R.string.text_followed);
        this.mDrawableBlock = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_block_black_24dp, R.color.tint_list_grey);
        this.mDrawableEdit = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_mode_edit_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowing = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_add_black_24dp, R.color.tint_list_grey);
        this.mDrawableFollowed = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_check_black_24dp, R.color.tint_list_grey);
        mDesireWidth = ((BaseActivity) context).mScreenWidthPixels / 2;
        mResizeOptions = new ResizeOptions(100, 100);
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
                .addOnClickListener(R.id.relativelayout_board_operate);

        ((SimpleDraweeView) holder.getView(R.id.img_card_image)).setAspectRatio(1.0f);

        Drawable dProgressImg = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        Drawable retryDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_retry_36dp, R.color.tint_list_grey);
        Drawable failDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_load_failed_36dp, R.color.tint_list_grey);

        new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_card_image)),
                String.format(mGeneralImgUrl, getFirstPinsFileKey(bean)))
                .setProgressbarImage(dProgressImg)
                .setRetryImage(retryDrawable)
                .setFailureIamge(failDrawable)
                .setResizeOptions(new ResizeOptions(mDesireWidth, mDesireWidth))
                .build();
        ((SimpleDraweeView) holder.getView(R.id.img_card_image)).setVisibility(View.VISIBLE);

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_first)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 1) {
            String firstImgUrl = String.format(mSmallImgUrl, bean.getPins().get(1).getFile().getKey());
            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_first)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .setResizeOptions(mResizeOptions)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 2) {
            String firstImgUrl = String.format(mSmallImgUrl, bean.getPins().get(2).getFile().getKey());

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .setResizeOptions(mResizeOptions)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 3) {
            String firstImgUrl = String.format(mSmallImgUrl, bean.getPins().get(3).getFile().getKey());

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .setResizeOptions(mResizeOptions)
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
