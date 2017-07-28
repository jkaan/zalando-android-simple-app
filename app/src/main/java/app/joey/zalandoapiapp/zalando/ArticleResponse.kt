package app.joey.zalandoapiapp.zalando

class JsonArticle(val id: String, val name: String, val media: Media, val units: List<Unit>)
class Image(val orderNumber: Int, val thumbnailHdUrl: String, val largeHdUrl: String)
class Media(val images: List<Image>)
class Unit(val id: String, val size: String, val price: Price)
class Price(val formatted: String)

class ArticleResponse {
    val content: List<JsonArticle> = ArrayList()
}