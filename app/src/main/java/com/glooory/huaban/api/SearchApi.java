package com.glooory.huaban.api;

import com.glooory.huaban.entity.PinsListBean;
import com.glooory.huaban.module.search.SearchHintBean;
import com.glooory.huaban.module.searchresult.ResultCountInfoBean;
import com.glooory.huaban.module.searchresult.ResultUserListBean;
import com.glooory.huaban.module.user.UserBoardListBean;
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

    //请求要搜索的关键字的采集，画板，用户数量
    //https://api.huaban.com/search/?q=keyword
    @GET("search")
    Observable<ResultCountInfoBean> httpResultCountInfoService(@Header(Constant.AUTHORIZATION) String authorization,
                                                               @Query("q") String keyWord);

    //搜索采集
    //https://api.huaban.com/search/?q=text&page=1&per_page=20
    @GET("search")
    Observable<PinsListBean> httpSearchPinsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                   @Query("q") String keyword,
                                                   @Query("page") int page,
                                                   @Query("per_page") int perPage);

    //搜索画板
    //https://api.huaban.com/search/boards/?q=text&page=1&per_page=20
    @GET("search/boards")
    Observable<UserBoardListBean> httpSearchBoardsService(@Header(Constant.AUTHORIZATION) String authorization,
                                                          @Query("q") String keyword,
                                                          @Query("page") int page,
                                                          @Query("per_page") int perPage);

    //搜索用户
    //https://api.huaban.com/search/users/?q=text&page=1&per_page=20
    @GET("search/people")
    Observable<ResultUserListBean> httpSearchUsersService(@Header(Constant.AUTHORIZATION) String authorization,
                                                           @Query("q") String keyword,
                                                           @Query("page") int page,
                                                           @Query("per_page") int perPage);

}
