package bitc.fullstack503.ordernetserver.mapper;


import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HQDashBoardMapper {

    //주문 현황
    OrderStatusStatsDTO selectOrderStatusStats();

    //대리점별 월간 매출 추이 그래프
    List<OrderMonthlySalesDTO> getMonthlySalesByBranch();

}
