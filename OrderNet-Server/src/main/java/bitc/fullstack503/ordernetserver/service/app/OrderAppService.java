package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.WHOrderAppItemDTO;

import java.util.List;

public interface OrderAppService {
  // 전체 주문 조회
  List<OrderAppDTO> getAllOrders();

  // 특정 주문 상세 조회
  OrderAppDTO getOrderById(String orderId);

  List<OrderAppDTO> getOrdersByWarehouse(String warehouseId);  // 물류센터 ID 기준으로 조회

  List<WHOrderAppItemDTO> getOrderItemsByWarehouseAndOrder(String orderId, String warehouseId);

  void processOutbound(String orderId, String warehouseId);
}
