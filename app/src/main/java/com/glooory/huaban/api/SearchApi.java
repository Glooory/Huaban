package com.glooory.huaban.api;

import com.glooory.huaban.module.search.SearchHintBean;
import com.glooory.huaban.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/16 0016 14:41.
 */
public interface SearchApi {

    //搜索关键字
    //https:api.huaban.com/search/hint?q=%df&cdfd
    @GET("search/hint/")
    Observable<SearchHintBean> httpSearchHintSewrvice(@Header(Constant.AUTHORIZATION) String authorization,
                                                      @Query("q") String key);


}
