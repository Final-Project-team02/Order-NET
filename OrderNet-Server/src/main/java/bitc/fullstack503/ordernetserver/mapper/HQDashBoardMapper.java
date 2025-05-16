package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HQDashBoardMapper {

  OrderStatusStatsDTO selectOrderStatusStats();
}
