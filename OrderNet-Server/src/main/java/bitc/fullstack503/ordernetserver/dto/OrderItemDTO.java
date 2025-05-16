package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private int orderItemId;
    private String branchId;
    private String orderItemName;
    private String orderId;
    private String partId;
    private String partName;
    private int orderItemQuantity;
    private BigDecimal orderItemPrice;
}