package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPaymentDetailDTO {
    private String branchId;
    private String branchName;
    private String partId;
    private String partName;
    private int orderItemQuantity;
    private BigDecimal orderItemPrice;
    private Date orderDate;
    private String orderStatus;
    private String orderId;
    private String orderDeny;
}
