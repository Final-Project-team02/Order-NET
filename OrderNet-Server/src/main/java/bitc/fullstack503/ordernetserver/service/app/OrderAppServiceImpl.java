package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.mapper.app.OrderAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAppServiceImpl implements OrderAppService {

  private final OrderAppMapper orderAppMapper;

  @Autowired
  public OrderAppServiceImpl(OrderAppMapper orderAppMapper) {
    this.orderAppMapper = orderAppMapper;
  }

  @Override
  public List<OrderAppDTO> getAllOrders() {
    return orderAppMapper.getAllOrders();
  }

  @Override
  public OrderAppDTO getOrderById(String orderId) {
    return orderAppMapper.getOrderById(orderId);
  }

  @Override
  public List<OrderAppDTO> getOrdersByWarehouse(String warehouseId) {
    return orderAppMapper.getOrdersByWarehouse(warehouseId); // 물류센터 ID를 이용한 조회
  }
}