package app.joey.zalandoapiapp

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.joey.zalandoapiapp.zalando.JsonArticle
import com.bumptech.glide.Glide

class ArticleClickListener(val article: JsonArticle?): View.OnClickListener {
    override fun onClick(v: View?) {
        val intent = Intent(v!!.context, DetailArticleActivity::class.java)
        intent.putExtra("articleName", article?.name)
        intent.putExtra("articleImageUrl", article?.media?.images?.get(0)?.largeHdUrl)
        v.context.startActivity(intent)
    }

}

class MyAdapter(val articles: List<JsonArticle>?) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(// each data item is just a string in this case
            var view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.view, parent, false)
        val vh = ViewHolder(v)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val textView = holder.view.findViewById(R.id.article_name) as TextView
        val imageView = holder.view.findViewById(R.id.article_image) as ImageView
        val priceView = holder.view.findViewById(R.id.article_price) as TextView

        val article = articles?.get(position)

        textView.text = article?.name
        priceView.text = article?.units?.get(0)?.price?.formatted

        holder.view.setOnClickListener(ArticleClickListener(articles?.get(position)))

        Glide.with(holder.view.context).load(articles?.get(position)?.media?.images?.get(0)?.largeHdUrl).into(imageView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        if (articles != null) return articles.size else return 0
    }
}


