package com.example.oapp.http

import android.graphics.Point
import com.example.oapp.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by jsxiaoshui on 2021/6/28
 */
interface ApiService {
    /***
     * 获取首页轮播图
     */
    @GET("banner/json")
    fun getBanners():Observable<HttpResult<List<Banner>>>
    /***
     * 获取首页置顶文章
     *
     */
    @GET("article/top/json")
    fun getTopArticles():Observable<HttpResult<ArrayList<HomeData.DatasBean>>>
    /***
     *获取普通文章
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int):Observable<HttpResult<HomeData>>

    /***
     * 知识体系列表
     */
    @GET("tree/json")
    fun getKnowledgeTree():Observable<HttpResult<List<KnowledgeData>>>

    /***
     * 公众号tab栏
     */
    @GET("wxarticle/chapters/json")
    fun getWechatTab():Observable<HttpResult<List<WeChatData>>>

    /***
     * 公众号tab栏列表详情
     */
    @GET("article/list/{page}/json")
    fun getWechatTabDetail(@Path("page") page:Int,@Query("cid") cid:Int):Observable<HttpResult<WeChatItemData>>

    /***
     * 项目tab栏
     */
    @GET("project/tree/json")
    fun getProjectTab():Observable<HttpResult<List<ProjectData>>>

    /***
     * 项目tab栏公众号列表
     */
    @GET("project/list/{page}/json")
    fun getProjectDetail(@Path("page") page:Int,@Query("cid") cid:Int):Observable<HttpResult<ProjectItemData>>

    /***
     * 项目tab栏
     */
    @GET("navi/json")
    fun getNavTab():Observable<HttpResult<List<NavBean>>>

    /***
     * Hot key
     */
    @GET("hotkey/json")
    fun getHotkey():Observable<HttpResult<List<HotBean>>>

    /***
     * Hot key
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchList(@Path("page") page:Int,@Field("k") key:String):Observable<HttpResult<HomeData>>

    /***
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun loginWanAndroid(@Field("username") username: String, @Field("password") password: String): Observable<HttpResult<LoginBean>>

    /**
     * 注册新用户
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                 @Field("password") password:String,
                 @Field("repassword") repassword:String):Observable<HttpResult<LoginBean>>

    /**
     * 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("/lg/coin/userinfo/json")
    fun getUserInfo(): Observable<HttpResult<UserInfoBody>>

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

    /**
     * 获取热词列表
     */
    @GET("hotkey/json")
    fun getHotKeyList():Observable<HttpResult<List<HotBean>>>


    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page:Int):Observable<HttpResult<CollectArticle>>

    /**
     * 获取积分记录
     */
    @GET("lg/coin/list/{page}/json")
    fun getScoreList(@Path("page") page:Int):Observable<HttpResult<ScoreBean>>

    /**
     * 获取积分列表
     */
    @GET("coin/rank/{page}/json")
    fun getPointList(@Path("page") page:Int):Observable<HttpResult<PointBean>>

    /**
     * 计划Todo
     */
    @POST("lg/todo/{status}/{type}/json/{page}")
    fun getTodoList(@Path("status") status:String,@Path("type") type:Int,@Path("page") page:Int):Observable<HttpResult<ToDoBean>>

    /**
     * 标记已完成或者删除
     */
    @POST("lg/todo/{type}/{id}/json")
    @FormUrlEncoded
    fun markOrDelete(@Path("type") type:String,@Path("id") id:Int,@Field("status") status:Int):Observable<HttpResult<ToDoBean.DatasBean>>

    @POST("lg/todo/{type}/{id}/json")
    fun delete(@Path("type") type:String,@Path("id") id:Int):Observable<HttpResult<ToDoBean.DatasBean>>



    @POST("lg/todo/add/json")
    @FormUrlEncoded
    fun addToDo(@Field("type") addType:Int,@Field("title") title:String,
                @Field("content") content:String,@Field("date") date:String,
                @Field("priority") priority:String):Observable<HttpResult<ToDoBean.DatasBean>>

    @POST("lg/todo/update/{id}/json")
    @FormUrlEncoded
    fun editToDo(@Path("id")id:Int,@Field("type") addType:Int, @Field("title") title:String,
                 @Field("content") content:String, @Field("date") date:String,
                 @Field("priority") priority:String):Observable<HttpResult<ToDoBean.DatasBean>>



}