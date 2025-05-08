// 결제 내역

package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPaymentDTO {

    private String partName;
    private Date orderDate;
    private String orderId;
    private String orderDeny;
}
