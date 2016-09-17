package com.glooory.huaban.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

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
 * Created by Glooory on 2016/9/17 0017 14:22.
 */
public class TypeBoardAdapter extends BaseQuickAdapter<UserBoardItemBean> {

    private final String mAttentionFormat; //画板的关注数量
    private final String mGatherFormat; // 画板的采集数量
    private final String mOperateFollowing; //关注
    private final String mOperateFollowed; //已关注

    private Context mContext;
    private String mGeneralImgUrl;
    private String mSmallImgUrl;
    private int mDesireWidth;


    public TypeBoardAdapter(Context context) {
        super(R.layout.cardview_type_board, null);
        mContext = context;
        Resources resources = mContext.getResources();
        mGeneralImgUrl = resources.getString(R.string.format_url_image_general);
        mSmallImgUrl = resources.getString(R.string.format_url_image_small);
        this.mGatherFormat = resources.getString(R.string.format_gather_number);
        this.mAttentionFormat = resources.getString(R.string.format_attention_number);
        this.mOperateFollowing = resources.getString(R.string.text_following);
        this.mOperateFollowed = resources.getString(R.string.text_followed);
        mDesireWidth = ((BaseActivity) context).mScreenWidthPixels / 2;
    }

    @Override
    protected void convert(BaseViewHolder holder, UserBoardItemBean bean) {
        boolean isFollowing = bean.isFollowing();
        String text;
        if (isFollowing) {
            text = mOperateFollowed;
        } else {
            text = mOperateFollowing;
        }

        //设置要展示的文字信息
        holder.setText(R.id.tv_board_title, bean.getTitle())
                .setText(R.id.tv_board_gather, String.format(mGatherFormat, bean.getPin_count()))
                .setText(R.id.tv_board_attention, String.format(mAttentionFormat, bean.getFollow_count()))
                .setText(R.id.tv_card_type_board_username, bean.getUser().getUsername())
                .setText(R.id.tv_board_operate, text)
                .addOnClickListener(R.id.linearlayout_image)
                .addOnClickListener(R.id.rl_card_type_board_user)
                .addOnClickListener(R.id.tv_board_operate);

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
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 2) {
            String firstImgUrl = String.format(mSmallImgUrl, bean.getPins().get(2).getFile().getKey());

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_second)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)).setAspectRatio(1.0f);
        if (bean.getPins().size() > 3) {
            String firstImgUrl = String.format(mSmallImgUrl, bean.getPins().get(3).getFile().getKey());

            new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_user_board_list_third)), firstImgUrl)
                    .setIsRadius(true, 8)
                    .build();
        }

        ((SimpleDraweeView) holder.getView(R.id.img_card_type_board_avatar)).setAspectRatio(1.0f);
        new FrescoLoader.Builder(mContext, ((SimpleDraweeView) holder.getView(R.id.img_card_type_board_avatar)),
                String.format(mSmallImgUrl, bean.getUser().getAvatarBean().getKey()))
                .setIsRadius(true, 4)
                .build();
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
