package com.xiocao.wanandroid.app

import com.google.gson.JsonElement
import com.xiocao.wanandroid.retrofit.bean.HttpResult
import com.xiocao.wanandroid.ui.category.Cate
import com.xiocao.wanandroid.ui.home.HomeBanner
import com.xiocao.wanandroid.ui.home.HomeList
import com.xiocao.wanandroid.ui.home.TypeList
import com.xiocao.wanandroid.ui.login.User
import com.xiocao.wanandroid.ui.project.ProjectTitle
import com.xiocao.wanandroid.ui.project.Projects
import com.xiocao.wanandroid.ui.search.HotSearch


import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * User : lijun
 * Date : 2018/3/12  16:37
 * Content : This is
 */
interface ApiService {
    @GET("article/list/{page}/json")
    fun getHomeList(@Path("page") page: Int): Observable<HttpResult<HomeList>>

    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<List<HomeBanner>>>

    @GET("tree/json")
    fun getCateInfo(): Observable<HttpResult<List<Cate>>>

    @GET("article/list/{page}/json")
    fun getTypeList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<TypeList>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<HttpResult<User>>

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): Observable<HttpResult<User>>

    @GET("lg/collect/list/{page}/json")
    fun getCollection(@Path("page") page: Int): Observable<HttpResult<TypeList>>

    @POST("lg/collect/{id}/json")
    fun addCollect(@Path("id") id: Int): Observable<HttpResult<JsonElement>>

    @POST("lg/uncollect_originId/{id}/json")
    fun delCollect(@Path("id") id: Int): Observable<HttpResult<JsonElement>>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun getSearch(@Path("page") page: Int, @Field("k") k: String): Observable<HttpResult<TypeList>>

    @GET("hotkey/json")
    fun getHotSearch(): Observable<HttpResult<List<HotSearch>>>

    @GET("project/tree/json")
    fun getProjectTitle(): Observable<HttpResult<List<ProjectTitle>>>

    @GET("project/list/{page}/json")
    fun getProjectList(@Path("page") page: Int,@Query("cid") cid:Int): Observable<HttpResult<Projects>>
}
