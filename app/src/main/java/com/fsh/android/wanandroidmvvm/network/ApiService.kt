package com.fsh.android.wanandroidmvvm.network

import com.fsh.android.wanandroidmvvm.network.response.BaseResponse
import com.fsh.android.wanandroidmvvm.network.response.EmptyResponse
import com.fsh.android.wanandroidmvvm.module.common.model.Article
import com.fsh.android.wanandroidmvvm.module.account.model.LoginResponse
import com.fsh.android.wanandroidmvvm.module.account.model.RegisterResponse
import com.fsh.android.wanandroidmvvm.module.collect.model.CollectResponse
import com.fsh.android.wanandroidmvvm.module.home.model.BannerResponse
import com.fsh.android.wanandroidmvvm.module.home.model.HomeArticleResponse
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareArticleResponse
import com.fsh.android.wanandroidmvvm.module.meshare.model.MeShareResponse
import com.fsh.android.wanandroidmvvm.module.navigation.model.NavigationTabNameResponse
import com.fsh.android.wanandroidmvvm.module.project.model.ProjectResponse
import com.fsh.android.wanandroidmvvm.module.project.model.ProjectTabResponse
import com.fsh.android.wanandroidmvvm.module.question.model.QuestionResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralHistoryListResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.fsh.android.wanandroidmvvm.module.rank.model.RankResponse
import com.fsh.android.wanandroidmvvm.module.search.model.HotKeyResponse
import com.fsh.android.wanandroidmvvm.module.search.model.SearchResultResponse
import com.fsh.android.wanandroidmvvm.module.square.model.SquareResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemArticleResponse
import com.fsh.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import com.fsh.android.wanandroidmvvm.module.todo.model.TodoPageResponse
import com.fsh.android.wanandroidmvvm.module.wechat.model.WeChatArticleResponse
import com.fsh.android.wanandroidmvvm.module.wechat.model.WeChatTabNameResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * Description:
 */

interface ApiService {

    @POST("/user/login")
    fun onLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<BaseResponse<LoginResponse>>

    @POST("/user/register")
    fun onRegister(
        @Query("username") username: String, @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Observable<BaseResponse<RegisterResponse>>

    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @GET("/banner/json")
    fun loadBanner(): Observable<BaseResponse<List<BannerResponse>>>

    @GET("/article/top/json")
    fun loadTopArticle(): Observable<BaseResponse<List<Article>>>

    @GET("/article/list/{pageNum}/json")
    fun loadHomeArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<HomeArticleResponse>>

    @GET("/wxarticle/chapters/json")
    fun loadWeChatTab(): Observable<BaseResponse<List<WeChatTabNameResponse>>>

    @GET("/wxarticle/list/{cid}/{pageNum}/json")
    fun loadWeChatArticles(@Path("cid") cid: Int, @Path("pageNum") page: Int)
            : Observable<BaseResponse<WeChatArticleResponse>>

    @GET("/tree/json")
    fun loadSystemTab(): Observable<BaseResponse<List<SystemTabNameResponse>>>

    @GET("/article/list/{pageNum}/json")
    fun loadSystemArticles(
        @Path("pageNum") pageNum: Int,
        @Query("cid") id: Int?
    ): Observable<BaseResponse<SystemArticleResponse>>

    @GET("/project/tree/json")
    fun loadProjectTab(): Observable<BaseResponse<List<ProjectTabResponse>>>

    @GET("/project/list/{pageNum}/json")
    fun loadProjectArticles(
        @Path("pageNum") pageNum: Int,
        @Query("cid") cid: Int
    ): Observable<BaseResponse<ProjectResponse>>

    @GET("/navi/json")
    fun loadNavigationTab(): Observable<BaseResponse<List<NavigationTabNameResponse>>>

    @GET("/lg/collect/list/{pageNum}/json")
    fun loadCollectArticle(@Path("pageNum") page: Int): Observable<BaseResponse<CollectResponse>>

    @POST("/lg/uncollect/{id}/json")
    fun unCollect(
        @Path("id") id: Int,
        @Query("originId") originId: Int
    ): Observable<BaseResponse<EmptyResponse>>

    @POST("lg/collect/add/json")
    fun addCollectArticle(
        @Query("title") title: String,
        @Query("author") author: String,
        @Query("link") link: String
    ): Observable<BaseResponse<EmptyResponse>>

    @GET("/lg/todo/v2/list/{pageNum}/json")
    fun loadTodoData(@Path("pageNum") pageNum: Int): Observable<BaseResponse<TodoPageResponse>>

    @POST("/lg/todo/add/json")
    fun addTodo(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("date") date: String,
        @Query("type") type: Int,
        @Query("priority") priority: Int
    ): Observable<BaseResponse<EmptyResponse>>

    @POST("/lg/todo/delete/{id}/json")
    fun deleteTodo(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @POST("/lg/todo/update/{id}/json")
    fun updateTodo(
        @Path("id") id: Int?,
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("date") date: String,
        @Query("type") type: Int,
        @Query("priority") priority: Int
    ): Observable<BaseResponse<EmptyResponse>>

    @POST("/lg/todo/done/{id}/json")
    fun finishTodo(
        @Path("id") id: Int,
        @Query("status") status: Int
    ): Observable<BaseResponse<EmptyResponse>>

    @GET("hotkey/json")
    fun loadHotKey(): Observable<BaseResponse<List<HotKeyResponse>>>

    @POST("/article/query/{pageNum}/json")
    fun loadSearchResult(
        @Path("pageNum") pageNum: Int,
        @Query("k") key: String
    ): Observable<BaseResponse<SearchResultResponse>>

    @GET("wenda/list/{pageNum}/json")
    fun loadQuestionList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<QuestionResponse>>

    @GET("user/lg/private_articles/{pageNum}/json")
    fun loadMeShareArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<MeShareResponse<MeShareArticleResponse>>>

    @GET("user_article/list/{pageNum}/json")
    fun loadSquareArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<SquareResponse>>

    @POST("lg/user_article/delete/{id}/json")
    fun deleteShareArticle(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @POST("lg/user_article/add/json")
    fun addShareArticle(
        @Query("title") title: String,
        @Query("link") link: String
    ): Observable<BaseResponse<EmptyResponse>>

    @GET("coin/rank/{pageNum}/json")
    fun loadRankList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<RankResponse>>

    @GET("lg/coin/userinfo/json")
    fun loadMeRankInfo(): Observable<BaseResponse<IntegralResponse>>

    @GET("/lg/coin/list/{pageNum}/json")
    fun loadIntegralHistory(@Path("pageNum") pageNum: Int) : Observable<BaseResponse<IntegralHistoryListResponse>>

    // 使用协程 + Retrofit2.6
    @POST("/lg/collect/{id}/json")
    suspend fun collectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>

    @GET("/banner/json")
    suspend fun loadBannerCo() : BaseResponse<List<BannerResponse>>

    @GET("/article/top/json")
    suspend fun loadTopArticleCo(): BaseResponse<List<Article>>

    @GET("/article/list/{pageNum}/json")
    suspend fun loadHomeArticleCo(@Path("pageNum") pageNum: Int): BaseResponse<HomeArticleResponse>

    @POST("/user/login")
    suspend fun onLoginCo(
        @Query("username") username: String,
        @Query("password") password: String
    ): BaseResponse<LoginResponse>

    @POST("/user/register")
    suspend fun onRegisterCo(
        @Query("username") username: String, @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseResponse<RegisterResponse>

    @GET("/wxarticle/chapters/json")
    suspend fun loadWeChatTabCo(): BaseResponse<List<WeChatTabNameResponse>>

    @GET("/wxarticle/list/{cid}/{pageNum}/json")
    suspend fun loadWeChatArticlesCo(@Path("cid") cid: Int, @Path("pageNum") page: Int)
            : BaseResponse<WeChatArticleResponse>

    @GET("/tree/json")
    suspend fun loadSystemTabCo(): BaseResponse<List<SystemTabNameResponse>>

    @GET("/article/list/{pageNum}/json")
    suspend fun loadSystemArticlesCo(
        @Path("pageNum") pageNum: Int,
        @Query("cid") id: Int?
    ): BaseResponse<SystemArticleResponse>

    @GET("/project/tree/json")
    suspend fun loadProjectTabCo(): BaseResponse<List<ProjectTabResponse>>

    @GET("/project/list/{pageNum}/json")
    suspend fun loadProjectArticlesCo(
        @Path("pageNum") pageNum: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectResponse>

    @GET("/navi/json")
    suspend fun loadNavigationTabCo(): BaseResponse<List<NavigationTabNameResponse>>

    @GET("/lg/collect/list/{pageNum}/json")
    suspend fun loadCollectArticleCo(@Path("pageNum") page: Int): BaseResponse<CollectResponse>
}