package app.joey.zalandoapiapp

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import app.joey.zalandoapiapp.zalando.JsonArticle
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView

interface ArticlesView: MvpLceView<List<JsonArticle>>

class MainActivity: MvpLceActivity<ConstraintLayout,
        List<JsonArticle>, ArticlesView, ArticlesPresenter>(), ArticlesView {
    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadArticles(searchTerm = null)
    }

    override fun setData(data: List<JsonArticle>?) {
        val recyclerView = findViewById(R.id.contentView) as RecyclerView

        val adapter = MyAdapter(articles = data)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged() // May or may not be necessary
    }

    override fun createPresenter(): ArticlesPresenter {
        return ArticlesPresenter()
    }

    override fun getErrorMessage(e: Throwable?, pullToRefresh: Boolean): String {
        return "Wrong"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById(R.id.contentView) as RecyclerView

        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(ArrayList<JsonArticle>())
        recyclerView.adapter = adapter

        (findViewById(R.id.search_button) as Button).setOnClickListener {
            val searchTextField = findViewById(R.id.search_field) as EditText
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchTextField.windowToken, 0)
            presenter.loadArticles(searchTerm = searchTextField.text.toString())
        }
    }
}
