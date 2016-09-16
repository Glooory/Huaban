package com.glooory.huaban.module.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.glooory.huaban.R;
import com.glooory.huaban.api.SearchApi;
import com.glooory.huaban.base.BaseActivity;
import com.glooory.huaban.httputils.RetrofitClient;
import com.glooory.huaban.module.type.TypeActivity;
import com.glooory.huaban.util.CompatUtils;
import com.glooory.huaban.util.Constant;
import com.glooory.huaban.util.SPUtils;
import com.glooory.huaban.util.Utils;
import com.glooory.huaban.widget.FlowLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/16 0016 14:07.
 */
public class SearchActivity extends BaseActivity {


    @BindView(R.id.actv_search)
    AutoCompleteTextView mActvSearch;
    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    @BindView(R.id.imgbtn_clear_history)
    ImageButton mImgbtnClearHistory;
    @BindView(R.id.flow_layout_history)
    FlowLayout mFlowHistory;
    @BindView(R.id.flow_layout_reference)
    FlowLayout mFlowReference;
    @BindView(R.id.scrollview_search)
    ScrollView mScrollview;

    @BindString(R.string.hint_null_history)
    String mNullHistory;

    private final int mItemCountEachLine = 3;
    private final int mItemMargin = 1;
    private final int mItemTVMargin = 10;
    private int mItemWidth;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mListHttpHint = new ArrayList<>();

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTAG() {
        return "SearchActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mImgbtnClearHistory.setImageDrawable(
                CompatUtils.getTintListDrawable(mContext, R.drawable.ic_close_black_24dp, R.color.tint_list_grey));
        initFlowReference(mFlowReference);

        initHintAdapter();
        initHintHttp();

        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: 2016/9/16 0016 launch result activity


            }
        });

        RxTextView.editorActions(mActvSearch, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_SEARCH;
            }
        })
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        initActionSearch();
                    }
                });

        initClearHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFlowHistory(mFlowHistory);
    }

    /**
     * 监听用户输入的关键字，联网查询相关关键字并展示在输入框下方
     */
    private void initHintHttp() {

        RxTextView.textChanges(mActvSearch)
                .observeOn(Schedulers.io())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() > 0; //过滤掉空输入
                    }
                })
                //debounce 方法  过滤掉由Observable发射的速率过快的数据
                .debounce(300, TimeUnit.MILLISECONDS)
                //switchMap 方法 每当源Observable发射一个新的数据项（Observable）时，
                //它将取消订阅并停止监视之前那个数据产生的Observable， 开始监视当前发射的这一个
                .switchMap(new Func1<CharSequence, Observable<SearchHintBean>>() {
                    @Override
                    public Observable<SearchHintBean> call(CharSequence charSequence) {
                        Logger.d(charSequence.toString());
                        return RetrofitClient.createService(SearchApi.class)
                                .httpSearchHintSewrvice(mAuthorization, charSequence.toString());
                    }
                })
                .map(new Func1<SearchHintBean, List<String>>() {
                    @Override
                    public List<String> call(SearchHintBean searchHintBean) {
                        return searchHintBean.getResult();
                    }
                })
                .filter(new Func1<List<String>, Boolean>() {
                    @Override
                    public Boolean call(List<String> strings) {
                        return strings != null && strings.size() > 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mListHttpHint.clear();
                        mListHttpHint.addAll(strings);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void initHintAdapter() {

        mAdapter = new SearchHintAdapter(mContext,
                android.R.layout.simple_spinner_dropdown_item, mListHttpHint);
        mActvSearch.setAdapter(mAdapter);
    }

    /**
     * 根据关键字开始搜索
     */
    private void initActionSearch() {
        if (mActvSearch.getText().length() > 0) {
            // TODO: 2016/9/16 0016 launch search result activity
        }
    }

    /**
     * 清除搜索历史
     */
    private void initClearHistory() {

        RxView.clicks(mImgbtnClearHistory)
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        mFlowHistory.removeAllViews();
                        SPUtils.remove(getApplicationContext(), Constant.HISTORYTEXT);
                        addChildTextTips(mFlowHistory, mNullHistory);
                    }
                });

    }

    /**
     * 初始化搜索记录view
     *
     * @param flowLayout
     */
    private void initFlowHistory(FlowLayout flowLayout) {

        flowLayout.removeAllViews();

        Set<String> mTextList = (Set<String>) SPUtils.get(getApplicationContext(),
                Constant.HISTORYTEXT, new HashSet<String>());
        if (!mTextList.isEmpty()) {
            for (String s : mTextList) {
                addChildText(flowLayout, s);
            }
        } else {
            addChildTextTips(flowLayout, mNullHistory);
        }

    }

    /**
     * 初始化推荐模块
     *
     * @param flowLayout
     */
    private void initFlowReference(FlowLayout flowLayout) {
        String[] mTypeNameList = getResources().getStringArray(R.array.type_names);
        String[] mTypeValueList = getResources().getStringArray(R.array.type_value);

        mItemWidth = Utils.getScreenWidth(mContext) / mItemCountEachLine - mItemMargin * 2;

        for (int i = 0; i < mTypeNameList.length; i++) {
            addChildButton(flowLayout, mTypeNameList[i], mTypeValueList[i], Constant.TYPE_ICON_RES_IDS[i], i);
        }
    }

    private void addChildTextTips(FlowLayout flowLayout, String text) {
        TextView tvChild = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemMargin, mItemMargin, mItemMargin, mItemMargin);
        tvChild.setText(text);
        tvChild.setLayoutParams(layoutParams);
        tvChild.setGravity(Gravity.LEFT);
        flowLayout.addView(tvChild);
    }

    /**
     * 添加搜索记录
     *
     * @param flowLayout
     * @param text
     */
    private void addChildText(FlowLayout flowLayout, String text) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_textview_history, flowLayout, false);
        textView.setText(text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/9/16 0016 launch search result activity
            }
        });

        flowLayout.addView(textView);
    }

    /**
     * 添加分类
     * @param flowLayout
     * @param text
     * @param type
     * @param resId
     */
    private void addChildButton(FlowLayout flowLayout, final String text, String type, int resId, final int position) {

        Button btnChild = new Button(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(mItemMargin, mItemMargin, mItemMargin, mItemMargin);
        btnChild.setCompoundDrawablesWithIntrinsicBounds(
                null,
                CompatUtils.getTintListDrawable(mContext, resId, R.color.tint_list_pink),
                null,
                null
        );
        btnChild.setText(text);
        btnChild.setBackgroundColor(Color.WHITE);
        btnChild.setLayoutParams(layoutParams);
        btnChild.setGravity(Gravity.CENTER);

        btnChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypeActivity.launch(SearchActivity.this, position);

            }
        });

        flowLayout.addView(btnChild);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                initActionSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
