package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;

import java.util.List;

/**
 * Created by Glooory on 2016/8/31 0031.
 */
public class PinQuickAdapter extends BaseQuickAdapter<PinsBean> {
    private Context mContext;
    private final String urlRoot;

    public PinQuickAdapter(Context context) {
        super(R.layout.card_item_pin, null);
        this.mContext = context;
        urlRoot = mContext.getResources().getString(R.string.urlImageRoot);
    }

    public PinQuickAdapter(Context context, int layoutResId, List<PinsBean> data) {
        super(layoutResId, data);
        this.mContext = context;
        urlRoot = mContext.getResources().getString(R.string.urlImageRoot);
    }

    @Override
    protected void convert(BaseViewHolder holder, PinsBean pinsBean) {
        //图片地址
        String urlImg = urlRoot + pinsBean.getFile().getKey();
        //头像地址
        String urlAvatar = urlRoot + pinsBean.getUser().getAvatar().getKey();

        //图片描述为空则不显示textview
        if (pinsBean.getRaw_text() == null || TextUtils.isEmpty(pinsBean.getRaw_text())) {
            holder.getView(R.id.item_card_pin_tvimgdes).setVisibility(View.GONE);
        } else {
            holder.setText(R.id.item_card_pin_tvimgdes, pinsBean.getRaw_text());
        }

        //采集和喜欢的图片
        Drawable pinDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_pin_12dp, R.color.grey_500);
        Drawable likeDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_favourite_12dp, R.color.grey_500);
        ((TextView) holder.getView(R.id.tv_item_card_pin_collection))
                .setCompoundDrawablesWithIntrinsicBounds(pinDrawable, null, null, null);
        ((TextView) holder.getView(R.id.tv_item_card_pin_like))
                .setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);

        holder.setText(R.id.tv_item_card_pin_collection, Utils.checkIfNeedConvert(pinsBean.getRepin_count()))
                .setText(R.id.tv_item_card_pin_like, Utils.checkIfNeedConvert(pinsBean.getLike_count()))
                .setText(R.id.item_card_pin_viadestv, setViaDesTextStyle(pinsBean))
                .addOnClickListener(R.id.item_card_pin_img_ll)
                .addOnClickListener(R.id.item_card_via_ll);

        float ratio = Utils.getAspectRatio(pinsBean.getFile().getWidth(), pinsBean.getFile().getHeight());
        ((SimpleDraweeView) holder.getView(R.id.item_card_pin_img)).setAspectRatio(ratio);

        Drawable mProgressImage = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);

        //加载pin图片
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.item_card_pin_img), urlImg)
                .setPlaceHolderImage(mContext.getResources().getDrawable(R.drawable.ic_account_box_white_36dp))
                .setFailureIamge(mContext.getResources().getDrawable(R.drawable.ic_account_box_white_36dp))
                .setProgressbarImage(mProgressImage)
                .build();

        //加载头像
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.item_card_pin_avaterimg), urlAvatar)
                .setIsRadius(true, 10)
                .build();
    }

    //将用户名和画板名称设置成深一点的颜色，便于区分
    private SpannableString setViaDesTextStyle(PinsBean pinsBean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pinsBean.getUser().getUsername())
                .append(" 采集到 ")
                .append(pinsBean.getBoard().getTitle());
        int useNameLength = pinsBean.getUser().getUsername().length();
        int boardTitleLength = pinsBean.getBoard().getTitle().length();
        SpannableString ss = new SpannableString(stringBuilder.toString());
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.primary_text)),
                0, useNameLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.primary_text)),
                useNameLength + 5, useNameLength + 5 + boardTitleLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
