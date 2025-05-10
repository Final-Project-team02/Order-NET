package bitc.fullstack.app.dto

import java.math.BigDecimal

data class OrderItemDTO(
    // 부품 코드
    val partId: String,

    // 수량
    val orderItemQuantity: Int,

    // 가격
    val orderItemPrice: Int
)
