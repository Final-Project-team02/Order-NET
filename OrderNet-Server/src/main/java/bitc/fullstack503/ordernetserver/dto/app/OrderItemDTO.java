package bitc.fullstack503.ordernetserver.dto.app;

import lombok.Data;

@Data
public class OrderItemDTO {
  
  // 주문에서 생성한 주문번호 매칠
  private String orderId;  

  // 부품 번호
  private String partId;

  // 부품 선택 수량
  private int orderItemQuantity;

  // 부품 가격
  private int orderItemPrice;
}
