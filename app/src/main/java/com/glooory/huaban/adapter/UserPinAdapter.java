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
public class UserPinAdapter extends BaseQuickAdapter<PinsBean>{
    private Context mContext;
    private String imgUrlRoot;

    public UserPinAdapter(Context context) {
        super(R.layout.card_user_item_pin, null);
        this.mContext = context;
        imgUrlRoot = context.getString(R.string.urlImageRoot);
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
        //加载图片
        Drawable progressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_user_item_pin),
                imgUrlRoot + bean.getFile().getKey())
                .setProgressbarImage(progressDrawable)
                .build();

    }
}
