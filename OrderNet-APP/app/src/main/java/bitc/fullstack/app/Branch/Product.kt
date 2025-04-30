package bitc.fullstack.app.Branch// 상품추가 더미 데이터 형식

data class Product(
    val code: String,
    val name: String,
    val spec: String,
    val price: Int,
    val imageResId: Int,
    var count: Int,
    var isChecked: Boolean
)

