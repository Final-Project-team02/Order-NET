// 조회 결과

package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {


        private String branchId;
        private String branchName;
        private String orderStatus;
        private String startDate;
        private String endDate;
        private String orderDeny;
        // + 조회 결과에 필요한 필드들 (예: 주문번호, 상품명, 수량 등)
        private String partId;
        private String partName;
        private int orderItemQuantity;
        private BigDecimal orderPrice;
        private Date orderDueDate;
        private BigDecimal orderItemPrice;
        private String orderId;
        private String orderDate;

}
