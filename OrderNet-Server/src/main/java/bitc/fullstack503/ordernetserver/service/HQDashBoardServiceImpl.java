package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import bitc.fullstack503.ordernetserver.mapper.HQDashBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HQDashBoardServiceImpl implements HQDashBoardService {

  @Autowired
  private HQDashBoardMapper hqDashBoardMapper;


  @Override
  public OrderStatusStatsDTO getOrderStatusStats() {
    return hqDashBoardMapper.selectOrderStatusStats();
  }

}
