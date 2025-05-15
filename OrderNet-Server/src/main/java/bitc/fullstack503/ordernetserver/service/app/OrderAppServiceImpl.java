package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.WHOrderAppItemDTO;
import bitc.fullstack503.ordernetserver.mapper.app.OrderAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderAppServiceImpl implements OrderAppService {

  private final OrderAppMapper orderAppMapper;

  @Autowired
  public OrderAppServiceImpl(OrderAppMapper orderAppMapper) {
    this.orderAppMapper = orderAppMapper;
  }

  @Override
  public List<OrderAppDTO> getOrdersByWarehouse(String warehouseId) {
    return orderAppMapper.getOrdersByWarehouse(warehouseId); // 물류센터 ID를 이용한 조회
  }

  @Override
  public List<WHOrderAppItemDTO> getOrderItemsByWarehouseAndOrder(String orderId, String warehouseId) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("orderId", orderId);
    paramMap.put("warehouseId", warehouseId);
    return orderAppMapper.getOrderItemsByWarehouseAndOrder(paramMap);
  }

  @Override
  public void processOutbound(String orderId, String warehouseId) {
    // 1. 해당 주문 + 물류센터의 아이템 목록 조회
    List<WHOrderAppItemDTO> items = getOrderItemsByWarehouseAndOrder(orderId, warehouseId);

    // 2. 출고 테이블에 기록
    Map<String, Object> outboundParam = new HashMap<>();
    outboundParam.put("orderId", orderId);
    outboundParam.put("warehouseId", warehouseId);
    orderAppMapper.insertOutbound(outboundParam);

    // 3. 재고 차감
    for (WHOrderAppItemDTO item : items) {
      Map<String, Object> stockParam = new HashMap<>();
      stockParam.put("warehouseId", warehouseId);
      stockParam.put("partId", item.getPartId());
      stockParam.put("quantity", item.getOrderItemQuantity());
      orderAppMapper.decreaseStock(stockParam);
    }

    // 4. 주문 아이템 상태를 '출고완료'로 업데이트
    orderAppMapper.updateOrderItemStatus(orderId);
  }
}