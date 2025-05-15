package bitc.fullstack503.ordernetserver.controller.web;


import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.PartDTO;
import bitc.fullstack503.ordernetserver.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // 주문번호, 날짜로 필터링
    @GetMapping("/{branchId}/orders")
    public List<OrderDTO> getOrdersByBranch(
            @PathVariable String branchId,
            @RequestParam(required = false) String orderId,  // 주문번호로 필터링
            @RequestParam(required = false) String startDate, // 시작일 필터링
            @RequestParam(required = false) String endDate) { // 종료일 필터링
        // 주문 필터링 서비스 호출
        return branchService.getOrdersByBranch(branchId, orderId, startDate, endDate);
    }

    // 주문 상세 부품 리스트 조회
    @GetMapping("/order/{orderId}/parts")
    public List<PartDTO> getPartsByOrderId(@PathVariable String orderId) {
        return branchService.getPartsByOrderId(orderId);
    }
}