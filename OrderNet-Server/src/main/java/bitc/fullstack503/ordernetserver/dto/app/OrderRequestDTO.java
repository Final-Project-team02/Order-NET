package bitc.fullstack503.ordernetserver.dto.app;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
  // 생성한 주문번호
  private String orderId;

  // 주문 일자
  private String orderDate;

  // 주문 납품 일자
  private String orderDueDate;

  // 주문 총 금액
  private int orderPrice;

  // 대리점 ID
  private String branchId;

  private List<OrderItemDTO> items;
}
