package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.*;
import bitc.fullstack503.ordernetserver.service.ClientService;
import bitc.fullstack503.ordernetserver.service.HQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/HQMain")
@RestController
public class WebController {

    @Autowired
    private HQService hqService;

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public String test() {
        return "test";
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