// 미결제 리스트

package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnpaidListDTO {
    private String branchId;
    private String branchName;
    private String partsId;
    private String partName;
    private int orderItemQuantity;
    private BigDecimal orderPrice;
    private Date orderDate;
    private Date orderDueDate;
    private String orderStatus;
    private String orderId;

}





