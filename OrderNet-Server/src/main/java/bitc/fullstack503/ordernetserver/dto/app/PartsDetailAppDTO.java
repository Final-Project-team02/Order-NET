package bitc.fullstack503.ordernetserver.dto.app;

import lombok.Data;

@Data
public class PartsDetailAppDTO {
    // 주문 상세 item DTO

    // 부품코드
    private String partId;
    // 부품명
    private String partName;
    // 부품 카테
    private String partCate;
    // 부품 선택 수량
    private int orderItemQuantity;
    // 부품 가격
    private int orderItemPrice;
    // 부품 상태
    private String orderItemStatus;
}
