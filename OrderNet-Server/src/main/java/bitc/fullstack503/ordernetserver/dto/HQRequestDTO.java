package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HQRequestDTO {
  private String warehouseId;   // 물류센터 ID
  private String partId;        // 부품 아이디
  private int inboundQuantity; // 요청할 부품 개수
  private BigDecimal inboundPrice;      // 입고 부품 가격

}
