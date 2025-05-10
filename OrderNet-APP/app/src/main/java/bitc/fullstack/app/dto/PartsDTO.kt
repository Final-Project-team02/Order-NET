package bitc.fullstack.app.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PartsDTO(

    // 부품 코드
    @SerializedName("partId")
    var partId: String = "",

    // 부품명
    @SerializedName("partName")
    var partName: String = "",

    // 부품 카테고리
    @SerializedName("partCate")
    var partCate: String = "",

    // 부품 가격
    @SerializedName("partPrice")
    var partPrice: Int = 0,

    // 부품 이미지
    @SerializedName("partImg")
    var partImg: String = "",

    // 선택 여부 저장
    var isChecked: Boolean = false,

    // 선택 부품 수량
    var count: Int = 1

): Serializable


fun List<PartsDTO>.toOrderItems(): List<OrderItemDTO> {
    return this.map {
        OrderItemDTO(
            // 부품 ID
            partId = it.partId,
            // 수량
            orderItemQuantity = it.count,
            // 가격
            orderItemPrice = it.partPrice
        )
    }
}
