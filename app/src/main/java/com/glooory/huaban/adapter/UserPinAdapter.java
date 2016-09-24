package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;

/**
 * Created by Glooory on 2016/9/5 0005 23:06.
 */
public class UserPinAdapter extends BaseQuickAdapter<PinsBean> {
    private Context mContext;
    private String mGeneralImgUrl;
    private boolean mIsMe;

    public UserPinAdapter(Context context, boolean isMe) {
        super(R.layout.card_user_item_pin, null);
        this.mContext = context;
        this.mGeneralImgUrl = context.getString(R.string.format_url_image_general);
        this.mIsMe = isMe;
    }

    @Override
    protected void convert(BaseViewHolder holder, PinsBean bean) {

        float ratio = Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight());
        ((SimpleDraweeView) holder.getView(R.id.img_user_item_pin)).setAspectRatio(ratio);

        if (Utils.checkIsGif(bean.getFile().getType())) {
            ((ImageButton) holder.getView(R.id.imgbtn_gif)).setVisibility(View.VISIBLE);
        } else {
            ((ImageButton) holder.getView(R.id.imgbtn_gif)).setVisibility(View.INVISIBLE);
        }

        Drawable pinDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_pin_12dp, R.color.grey_500);
        Drawable likeDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_favourite_12dp, R.color.grey_500);
        ((TextView) holder.getView(R.id.tv_user_item_collection))
                .setCompoundDrawablesWithIntrinsicBounds(pinDrawable, null, null, null);
        ((TextView) holder.getView(R.id.tv_user_item_like))
                .setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);

        if (bean.getRaw_text() != null && !TextUtils.isEmpty(bean.getRaw_text())) {
            holder.setText(R.id.tv_user_item_des, bean.getRaw_text());
        } else {
            ((TextView) holder.getView(R.id.tv_user_item_des)).setVisibility(View.GONE);
        }

        //设置文字信息和监听器
        holder.setText(R.id.tv_user_item_collection, Utils.checkIfNeedConvert(bean.getRepin_count()))
                .setText(R.id.tv_user_item_like, Utils.checkIfNeedConvert(bean.getLike_count()))
                .addOnClickListener(R.id.linearlayout_user_pin);

//        //如果当前采集是自己的采集设置longclicklistener
//        if (mIsMe) {
//            holder.addOnLongClickListener(R.id.card_user_pin)
//                    .addOnClickListener(R.id.imgbtn_delete);
//        }

        //加载图片
        Drawable progressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        Drawable retryDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_retry_36dp, R.color.tint_list_grey);
        Drawable failDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_load_failed_36dp, R.color.tint_list_grey);

        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_user_item_pin),
                String.format(mGeneralImgUrl, bean.getFile().getKey()))
                .setProgressbarImage(progressDrawable)
                .setRetryImage(retryDrawable)
                .setFailureIamge(failDrawable)
                .build();
        ((SimpleDraweeView) holder.getView(R.id.img_user_item_pin)).setVisibility(View.VISIBLE);

    }
}
