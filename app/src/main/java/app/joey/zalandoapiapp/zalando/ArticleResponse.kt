package app.joey.zalandoapiapp.zalando

data class JsonArticle(val id: String, val name: String, val media: Media, val units: List<Unit>)
data class Image(val orderNumber: Int, val thumbnailHdUrl: String, val largeHdUrl: String)
data class Media(val images: List<Image>)
data class Unit(val id: String, val size: String, val price: Price)
data class Price(val formatted: String)
data class ArticleResponse(val content: List<JsonArticle>)