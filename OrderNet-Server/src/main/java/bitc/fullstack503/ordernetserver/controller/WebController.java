package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.*;
import bitc.fullstack503.ordernetserver.service.ClientService;
import bitc.fullstack503.ordernetserver.service.HQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/HQMain")
@RestController
public class WebController {

    @Autowired
    private HQService hqService;

    // 새로 들어온 주문 건수
    private int newOrderCount = 0;
    // 지난 주문 건수
    private int lastOrderCount = 0;

    @GetMapping("")
    public String test() {
        return "test";
    }

    // 서버 시간 반환
    @GetMapping("/server-time")
    public Map<String, String> getServerTime() {
        ZonedDateTime nowKST = ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul"));

        // ISO 8601 포맷 (예: "2025-05-14T13:45:00+09:00")
        String isoTime = nowKST.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Map<String, String> response = new HashMap<>();
        response.put("time", isoTime);

        return response;
    }

    // 새로운 주문 건수 조회
    @GetMapping("/orderCount")
    public int checkNewOrders() {
        // 현재 주문건수
        int currentOrderCount = hqService.getCurrentOrderCount();
        // 현재 주문 건수 - 이전 주문건수
        newOrderCount = currentOrderCount - lastOrderCount;
        // 이전 주문건수 현재 주문 건수로 최신화
        lastOrderCount = currentOrderCount;

        if (newOrderCount > 0) {
            // 새로운 주문이 있으면 서버 로그에 출력
            System.out.println("새로운 주문이 " + newOrderCount + "건 들어왔습니다.");
        }
        return newOrderCount;
    }

    // 미결제 리스트
    @GetMapping("/order-item-info")
    public List<UnpaidListDTO> getOrderItemInfoList() {
        return hqService.getOrderItemInfoList();
    }

//    발주 내역
    @GetMapping("/orderList")
    public List<OrderListDTO> getOrderList() {
        return hqService.getOrderList();
    }

    //    결제 확인
    @GetMapping("/payment")
    public List<FindPaymentDTO> getFindPaymentList() {
        return hqService.getFindPaymentList();
    }

    //    결제 or 반려 상세
    @GetMapping("/paymentDetail")
    public List<FindPaymentDetailDTO> getFindPaymentDetailList() { return hqService.getFindPaymentDetailList();}

    //    검색
    @PostMapping("/search")
    public List<SearchDTO> searchOrders(@RequestBody SearchDTO dto) {
        System.out.println("Received search params: " + dto);
        return hqService.getFilteredOrders(dto);
    }

    // 단일 , 복수 주문 처리
    @PostMapping("/update")
    public ResponseEntity<?> updateOrders(@RequestBody UpdateOrdersRequest request) {
        if (request.getOrderIdList() != null && !request.getOrderIdList().isEmpty()) {
            // 복수 처리
            int count = hqService.updateMultipleOrderStatusAndDeny(
                    request.getOrderIdList(),
                    request.getOrderStatus(),
                    request.getOrderDeny()
            );
            return ResponseEntity.ok(count + "건 처리 완료");
        } else if (request.getOrderId() != null) {
            // 단일 처리
            int result = hqService.updateOrderStatusAndDeny(
                    request.getOrderId(),
                    request.getOrderStatus(),
                    request.getOrderDeny()
            );
            return ResponseEntity.ok("1건 처리 완료");
        } else {
            return ResponseEntity.badRequest().body("주문 정보가 없습니다.");
        }
    }

}