package app.joey.zalandoapiapp

import app.joey.zalandoapiapp.zalando.ArticleResponse
import app.joey.zalandoapiapp.zalando.ZalandoService
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesPresenter: MvpBasePresenter<ArticlesView>() {
    val service = ZalandoService()

    fun loadArticles(searchTerm: String?) {
        val articleCall: Call<ArticleResponse>

        if (searchTerm != null) {
            articleCall = service.getArticles(searchTerm)
        } else {
            articleCall = service.getAllArticles()
        }

        articleCall.enqueue(object: Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>?, response: Response<ArticleResponse>?) {
                if (isViewAttached) {
                    view.setData(response?.body()?.content)
                    view.showContent()
                }
            }

            override fun onFailure(call: Call<ArticleResponse>?, t: Throwable?) {
                if (isViewAttached) {
                    view.showError(t, false)
                }
            }
        })
    }
}