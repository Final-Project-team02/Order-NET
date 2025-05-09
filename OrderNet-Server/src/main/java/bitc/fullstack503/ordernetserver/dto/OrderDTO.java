package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String branchId;
    private Date orderDate;
    private String orderStatus;
    private BigDecimal orderPrice;
    private String orderDeny;
    private String branchName;

    // 주문 상세 항목 리스트 (주문 1건에 여러 상세 가능)
    private List<OrderItemDTO> orderItems;
}