package bitc.fullstack.app.dto

data class BranchOrderDTO(

    // 주문번호
    val orderId: String,

    // 대리점명
    val branchName: String,

    // 주문날짜
    val orderDate: String,

    // 주문 납품 날짜
    val orderDueDate: String,

    // 주문 상태
    val orderStatus: String,

    // 주문 가격
    val orderPrice: Int,

    // 주문 - 상세 개수
    val orderItemCount: Int,

    val partsList: Map<String, PartsDTO>

)
