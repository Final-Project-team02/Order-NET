package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;


import java.util.List;

public interface HQDashBoardService {

    //주문현황
    OrderStatusStatsDTO getOrderStatusStats();

    //  대리점별 월간 매출 추이 그래프
    List<OrderMonthlySalesDTO> getMonthlySalesByBranch();

}
