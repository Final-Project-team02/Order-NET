package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.WHOrderAppItemDTO;

import java.util.List;

public interface OrderAppService {

  List<OrderAppDTO> getOrdersByWarehouse(String warehouseId);  // 물류센터 ID 기준으로 조회

  List<WHOrderAppItemDTO> getOrderItemsByWarehouseAndOrder(String orderId, String warehouseId);

  void processOutbound(String orderId, String warehouseId);
}
