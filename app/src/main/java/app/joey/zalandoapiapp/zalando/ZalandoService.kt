package app.joey.zalandoapiapp.zalando

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ZalandoService {
    var service: Zalando
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.zalando.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        service = retrofit.create(Zalando::class.java)
    }

    fun getAllArticles(): Call<ArticleResponse> {
        return service.getArticles()
    }

    fun getArticles(searchTerm: String): Call<ArticleResponse> {
        return service.getArticles(searchTerm)
    }
}
