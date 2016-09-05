package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
        super(null);
        this.mContext = context;
        imgUrlRoot = context.getString(R.string.urlImageRoot);
    }

    @Override
    protected void convert(BaseViewHolder holder, PinsBean bean) {

        float ratio = Utils.getAspectRatio(bean.getFile().getWidth(), bean.getFile().getHeight());
        ((SimpleDraweeView) holder.getView(R.id.img_user_item_pin)).setAspectRatio(ratio);

        //设置文字信息和监听器
        holder.setText(R.id.tv_user_item_des, bean.getRaw_text())
                .setText(R.id.tv_user_item_collection, bean.getRepin_count())
                .setText(R.id.tv_user_item_like, bean.getLike_count())
                .addOnClickListener(R.id.linearlayout_user_pin)
                .addOnClickListener(R.id.tv_user_item_collection)
                .addOnClickListener(R.id.tv_user_item_like);

        //加载图片
        Drawable progressDrawable = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_petal, R.color.tint_list_pink);
        new FrescoLoader.Builder(mContext, (SimpleDraweeView) holder.getView(R.id.img_user_item_pin),
                imgUrlRoot + bean.getFile().getKey())
                .setProgressbarImage(progressDrawable)
                .build();

    }
}
