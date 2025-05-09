package bitc.fullstack503.ordernetserver.mapper.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderAppMapper {
  // 전체 주문 조회 (예시)
  List<OrderAppDTO> getAllOrders();

  // 특정 주문 조회
  OrderAppDTO getOrderById(String orderId);

  // 특정 물류센터에 대한 주문 조회
  List<OrderAppDTO> getOrdersByWarehouse(String warehouseId);  // 물류센터 ID 기반으로 주문 조회
}
