package bitc.fullstack503.ordernetserver.controller.web;


import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import bitc.fullstack503.ordernetserver.service.HQDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/HQDashBoard")
public class HQDashBoardController {

    @Autowired
    private HQDashBoardService hqDashBoardService;

    //  주문 현황
    @GetMapping("/order/stats")
    public OrderStatusStatsDTO getOrderStats() {
        return hqDashBoardService.getOrderStatusStats();
    }

    //  대리점별 월간 매출 추이 그래프
    @GetMapping("/order/monthly-sales")
    public List<OrderMonthlySalesDTO> getMonthlySalesByBranch() {
        return hqDashBoardService.getMonthlySalesByBranch();
    }

}
