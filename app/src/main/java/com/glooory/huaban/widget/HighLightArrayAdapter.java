package com.glooory.huaban.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Glooory on 2016/9/17 0017 22:19.
 * 自定义ArrayAdapter
 * 之前选择的item在展开时会highlight出来
 */
public class HighLightArrayAdapter extends ArrayAdapter<CharSequence> {

    private int mSelectedIndex = -1;


    public void setSelection(int position) {
        mSelectedIndex =  position;
        notifyDataSetChanged();
    }

    public HighLightArrayAdapter(Context context, int resource, CharSequence[] objects) {
        super(context, resource, objects);
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView =  super.getDropDownView(position, convertView, parent);

        if (position == mSelectedIndex) {
            itemView.setBackgroundColor(Color.rgb(233,30,99));
        } else {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        return itemView;
    }
}
