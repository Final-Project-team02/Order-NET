package bitc.fullstack503.ordernetserver.service;


import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import bitc.fullstack503.ordernetserver.mapper.HQDashBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HQDashBoardServiceImpl implements HQDashBoardService {

    @Autowired
    private HQDashBoardMapper hqDashBoardMapper;

    // 주문현황
    @Override
    public OrderStatusStatsDTO getOrderStatusStats() {
        return hqDashBoardMapper.selectOrderStatusStats();
    }

    //  대리점별 월간 매출 추이 그래프
    @Override
    public List<OrderMonthlySalesDTO> getMonthlySalesByBranch() {
        return hqDashBoardMapper.getMonthlySalesByBranch();
    }

}
