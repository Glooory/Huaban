package com.glooory.huaban.module.search;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Glooory on 2016/9/16 0016 14:54.
 * 用户输入关键字搜索时的相关关键字的Adapter
 */
public class SearchHintAdapter extends ArrayAdapter<String> {
    private Filter mFilter;
    private List<String> mObjects;

    public SearchHintAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.mObjects = objects;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new HintFilter();
        }
        return mFilter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     * 重写过滤类 自定义一个不会过滤任何数的Filter
     */
    private class HintFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Object> suggestions = new ArrayList<>();
            for (String s : mObjects) {
                suggestions.add(s);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mObjects = (List<String>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
