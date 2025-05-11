package bitc.fullstack.app.dto

data class BranchCountDTO(

    // 대리점 명
    val branchName: String,

    // 주문 수 - 승인대기
    val orderCount: Int,

    // 접수 된 주문 수 - 승인 완료, 출고 대기
    val acceptCount: Int,

    // 출고 된 주문 수 - 출고
    val deliveryCount: Int,

    // 반려 된 주문 수
    val holdCount: Int
)
