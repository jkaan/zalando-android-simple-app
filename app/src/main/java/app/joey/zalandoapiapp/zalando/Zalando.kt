package app.joey.zalandoapiapp.zalando

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Zalando {

    @Headers("Accept-Language: nl-NL")
    @GET("articles")
    fun getArticles(): Call<ArticleResponse>

    @Headers("Accept-Language: nl-NL")
    @GET("articles")
    fun getArticles(@Query("fullText") searchTerm: String): Call<ArticleResponse>
}