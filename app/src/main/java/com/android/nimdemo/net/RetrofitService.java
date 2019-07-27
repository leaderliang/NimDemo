package com.android.nimdemo.net;


import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * TODO ApiService
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2019/04/23 15:29
 */
public interface RetrofitService {

    @FormUrlEncoded
    @Headers({"From:1", "Content-Type:application/json"})
    @POST("ms/im/create")
    Observable<BaseResponse<NimAccountInfo>> createNimAccountId(@FieldMap Map<String, String> params);


    /**
     * 根据接口创建云信ID
     *
     * @param userId
     * @param accid
     * @param userType
     * @param channel
     * @return
     */
    @FormUrlEncoded
    @Headers({"From:1", "Content-Type:application/json"})
    @POST("ms/im/create")
    Observable<BaseResponse<NimAccountInfo>> createNimAccountId(
            @Field("userId") String userId, @Field("accid") String accid,
            @Field("userType") String userType, @Field("channel") String channel);


    @Headers({"From:1", "Content-Type:application/json"})
    @POST("ms/im/create")
    Observable<BaseResponse<NimAccountInfo>> createNimAccountId(@Body NimUser bean);


    @Headers("From:1")
    @GET("ms/im/allotService")
    Observable<BaseResponse<NimAccountInfo>> allotNimAccountId(@Query("accid") String accid,
                                                               @Query("userType") Integer userType,
                                                               @Query("channel") String channel,
                                                               @Query("sceneType") Integer sceneType);


}
