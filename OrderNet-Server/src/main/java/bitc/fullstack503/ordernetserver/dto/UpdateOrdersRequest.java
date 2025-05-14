// 결제, 반려 처리

package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrdersRequest {
    private String orderId;                // 단일 처리용
    private List<String> orderIdList;
    private String orderStatus;
    private String orderDeny;
}
