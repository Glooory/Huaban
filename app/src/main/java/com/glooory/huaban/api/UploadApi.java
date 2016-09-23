package com.glooory.huaban.api;


import com.glooory.huaban.module.gather.GatherResultBean;
import com.glooory.huaban.module.gather.UploadResultBean;
import com.glooory.huaban.util.Constant;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Glooory on 2016/9/23 0023 10:59.
 */

public interface UploadApi {

    //上传本地图片
    //https://api.huaban.com/upload
    @Multipart
    @POST("upload")
    Observable<UploadResultBean> httpUploadImgService(@Header(Constant.AUTHORIZATION) String authorization,
                                                      @Part MultipartBody.Part photo);

    //将上传的图片采集到自己的画板中
    //https://api.huaban.com/pins
    // body : board_id=31757169&text=108a91c24aea1962f1cce9fc3e75d7a0&check=true&file_id=115536732&via=1&share_button=0
    @FormUrlEncoded
    @POST("pins/")
    Observable<GatherResultBean> httpGatherService(@Header(Constant.AUTHORIZATION) String authorization,
                                                   @Field("board_id") String boardId,
                                                   @Field("text") String des,
                                                   @Field("file_id") String fileId,
                                                   @Field("via") int via,
                                                   @Field("check") boolean check,
                                                   @Field("share_button") int shareButton);


}
