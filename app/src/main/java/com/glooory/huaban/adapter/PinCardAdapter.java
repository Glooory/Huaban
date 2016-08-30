package com.glooory.huaban.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.huaban.R;
import com.glooory.huaban.entity.PinsBean;
import com.glooory.huaban.httputils.FrescoLoader;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Glooory on 2016/8/28 0028.
 */
public class PinCardAdapter extends RecyclerView.Adapter<PinCardAdapter.PinHolder> {
    private List<PinsBean> mPinsList = new ArrayList<>(20);
    private Context mContext;
    private final String url_root;

    public PinCardAdapter(Context context) {
        this.mContext = context;
        url_root = mContext.getResources().getString(R.string.urlImageRoot);
    }

    @Override
    public PinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pin_card, parent, false);
        return new PinHolder(view);
    }

    @Override
    public void onBindViewHolder(PinHolder holder, int position) {
        PinsBean pin = mPinsList.get(position);
        onBindData(holder, pin);
    }

    private void onBindData(PinHolder holder, PinsBean pinsBean) {
        if (!TextUtils.isEmpty(pinsBean.getRaw_text())) {
            //// TODO: 2016/8/29 0029 if this text is empty should it be invisibale?
        }
        String url_img = url_root + pinsBean.getFile().getKey();
        String url_avatar = url_root + pinsBean.getUser().getAvatar().getKey();
        float ratio = Utils.getAspectRatio(pinsBean.getFile().getWidth(), pinsBean.getFile().getHeight());
        //设置加载图像的宽高比
        holder.pinImg.setAspectRatio(ratio);
        holder.pinViaAvatarImg.setAspectRatio(1.0f);
        //占位图
        Drawable mProgressImg = CompatUtils.getTintListDrawable(mContext, R.drawable.ic_toys_black_48dp, R.color.tint_list_pink);

        new FrescoLoader.Builder(mContext, holder.pinImg, url_img)
                .setProgressbarImage(mProgressImg)
                .build();

        new FrescoLoader.Builder(mContext, holder.pinViaAvatarImg, url_avatar)
                .setIsRadius(true, 10)
                .build();

        holder.pinDesTv.setText(pinsBean.getRaw_text());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("由 ")
                .append(pinsBean.getUser().getUsername())
                .append(" 采集到 ")
                .append(pinsBean.getBoard().getTitle());
        int useNameLength = pinsBean.getUser().getUsername().length();
        int boardTitleLength = pinsBean.getBoard().getTitle().length();
        SpannableString ss = new SpannableString(stringBuilder.toString());
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.primary_text)),
                2, useNameLength + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.primary_text)),
                useNameLength + 7, useNameLength + 7 + boardTitleLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.pinViaDesTv.setText(ss);

    }

    @Override
    public int getItemCount() {
        if (mPinsList.size() > 0) {
            return mPinsList.size();
        }
        return 0;
    }

    public List<PinsBean> getPinsList() {
        return mPinsList;
    }

    public void setPinsList(List<PinsBean> pinsList) {
        this.mPinsList = pinsList;
        notifyDataSetChanged();
        Logger.d("adapter setPinsList excuted");

    }

    public void addPinsList(List<PinsBean> list) {
        this.mPinsList.addAll(list);
        notifyDataSetChanged();
    }

    class PinHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView pinImg;
        private TextView pinDesTv;
        private SimpleDraweeView pinViaAvatarImg;
        private TextView pinViaDesTv;

        public PinHolder(View itemView) {
            super(itemView);
            pinImg = (SimpleDraweeView) itemView.findViewById(R.id.item_card_pin_img);
            pinDesTv = (TextView) itemView.findViewById(R.id.item_card_pin_tvimgdes);
            pinViaAvatarImg = (SimpleDraweeView) itemView.findViewById(R.id.item_card_pin_avaterimg);
            pinViaDesTv = (TextView) itemView.findViewById(R.id.item_card_pin_viadestv);
        }
    }

}
