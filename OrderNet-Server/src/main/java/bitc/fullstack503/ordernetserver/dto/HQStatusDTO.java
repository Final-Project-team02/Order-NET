package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HQStatusDTO {
//  부품
  private String partId;          // 부품 아이디
  private String partName;        // 부품 명
  private String partCate;        // 부품 카테고리
  private String partImg;         // 부품 이미지
  private BigDecimal partPrice;          // 부품 값

//  물류센터
  private String warehouseId;   // 물류센터 ID
  private String warehouseName;   // 물류센터 명
  private String warehouseCate;   // 물류센터 카테고리
  private int stockQuantity;      // 재고 수량
}
