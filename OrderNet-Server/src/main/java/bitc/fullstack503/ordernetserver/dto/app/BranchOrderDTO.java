package bitc.fullstack503.ordernetserver.dto.app;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BranchOrderDTO {
    // 주문번호
    private String orderId;

    // 대리점 ID
    private String branchId;

    // 주문날짜
    private String orderDate;

    // 주문 납품 마감 일자
    private String orderDueDate;

    // 주문 상태
    private String orderStatus;

    // 주문 가격
    private int orderPrice;

    // 주문 상세 개수
    private int orderItemCount;

    // 대리점 명
    private String branchName;

    private Map<String, PartsDetailAppDTO> partsList;

}
