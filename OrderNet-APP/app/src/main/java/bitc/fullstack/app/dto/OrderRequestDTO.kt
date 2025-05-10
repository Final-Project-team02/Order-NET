package bitc.fullstack.app.dto

import java.math.BigDecimal


data class OrderRequestDTO(
    // 주문일자
    val orderDate: String,

    // 납품일자
    val orderDueDate: String,

    // 총 금액
    val orderPrice: Int,

    // 대리점 ID
    val branchId: String,

    // 주문 항목 (OrderItem 리스트)
    val items: List<OrderItemDTO>
)
