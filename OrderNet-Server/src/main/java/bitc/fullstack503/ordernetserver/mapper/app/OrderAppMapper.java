package bitc.fullstack503.ordernetserver.mapper.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.WHOrderAppItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderAppMapper {
  // 전체 주문 조회 (예시)
  List<OrderAppDTO> getAllOrders();

  // 특정 주문 조회
  OrderAppDTO getOrderById(String orderId);

  // 특정 물류센터에 대한 주문 조회
  List<OrderAppDTO> getOrdersByWarehouse(String warehouseId);  // 물류센터 ID 기반으로 주문 조회

  List<WHOrderAppItemDTO> getOrderItemsByWarehouseAndOrder(Map<String, Object> params);

  // 출고 테이블에 기록
  void insertOutbound(Map<String, Object> params);

  // 출고 수량만큼 재고 차감
  void decreaseStock(Map<String, Object> params);

  void updateOrderItemStatus(@Param("orderId") String orderId);

}
