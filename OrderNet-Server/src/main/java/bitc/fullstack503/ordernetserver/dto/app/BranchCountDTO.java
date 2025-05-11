package bitc.fullstack503.ordernetserver.dto.app;

import lombok.Data;

@Data
public class BranchCountDTO {

    // 대리점 명
    private String branchName;

    // 주문 수 - 승인대기
    private int orderCount;

    // 접수 된 주문 수 - 승인 완료, 출고 대기
    private int acceptCount;

    // 출고 된 주문 수 - 출고
    private int deliveryCount;

    // 반려 된 주문 수
    private int holdCount;
}
