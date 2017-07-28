package app.joey.zalandoapiapp

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import java.io.IOException
import android.view.inputmethod.InputMethodManager
import app.joey.zalandoapiapp.zalando.ArticleResponse
import app.joey.zalandoapiapp.zalando.JsonArticle
import app.joey.zalandoapiapp.zalando.ZalandoService


class MainActivity: AppCompatActivity(), Callback, AnkoLogger {
    override fun onArticleCollectionLoaded(articles: ArticleResponse?) {
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        val adapter = MyAdapter(articles = articles?.content)
        recyclerView.adapter = adapter
    }

    var service: ZalandoService = ZalandoService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(ArrayList<JsonArticle>())
        recyclerView.adapter = adapter

        (findViewById(R.id.search_button) as Button).setOnClickListener {
            val searchTextField = findViewById(R.id.search_field) as EditText
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchTextField.windowToken, 0)

            val asyncTask = ArticlesAsyncTask(
                    callback = this,
                    service = service,
                    searchTerm = searchTextField.text.toString()
            )
            asyncTask.execute()
        }
    }

    class ArticlesAsyncTask(
            val callback: Callback,
            val service: ZalandoService,
            val searchTerm: String?
    ): AsyncTask<Void, Void, ArticleResponse>(), AnkoLogger {
        override fun doInBackground(vararg params: Void?): ArticleResponse? {
            try {
                val articleCall: Call<ArticleResponse>

                if (searchTerm != null) {
                    articleCall = service.getArticles(searchTerm)
                } else {
                    articleCall = service.getAllArticles()
                }

                val response = articleCall.execute()

                if (response.isSuccessful) {
                    return response.body()
                }
                return ArticleResponse()
            } catch (e: IOException) {
                e.printStackTrace()
                return ArticleResponse()
            }
        }

        override fun onPostExecute(result: ArticleResponse?) {
            super.onPostExecute(result)
            callback.onArticleCollectionLoaded(result)
        }

    }
}

interface Callback {
    fun onArticleCollectionLoaded(articles: ArticleResponse?)
}
