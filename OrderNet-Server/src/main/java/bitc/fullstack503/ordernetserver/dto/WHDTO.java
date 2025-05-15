package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

@Data
//    물류센터 입고재고 DTO
public class WHDTO {

    private String partId; //.부품 아이디
    private String partName ; //부품 이름
    private String partCate; //부품 카테
    private int stockQuantity;// 부품 수량
    private int partPrice;// 부품 가격

    //-----------입고 DTO
    private int inboundQuantity; //입고 수량
    private String inboundDate; //입고 날짜

    // 출고 재고 관리
    private int orderItemId; //상세 주문 번호
    private String orderId; //주문번호
    private int orderItemQuantity; //주문수량
    private String branchName; //대리점명
    private String orderItemStatus; //주문상태
    private String orderDueDate; //배송 원하는 날짜

    //-----------------출구 DTO

    private int outboundId;
    private String warehouseId;
    private String outboundDate;

    private int totalQuantity;

}
