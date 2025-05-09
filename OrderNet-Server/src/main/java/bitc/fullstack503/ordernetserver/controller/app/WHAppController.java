package bitc.fullstack503.ordernetserver.controller.app;

import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.service.app.OrderAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class WHAppController {

  private final OrderAppService orderAppService;

  @Autowired
  public WHAppController(OrderAppService orderAppService) {
    this.orderAppService = orderAppService;
  }

  // 모든 주문 조회
  @GetMapping("/")
  public List<OrderAppDTO> getAllOrders() {
    return orderAppService.getAllOrders();
  }

  // 특정 주문 조회 (orderId로)
  @GetMapping("/{orderId}")
  public OrderAppDTO getOrderById(@PathVariable("orderId") String orderId) {
    return orderAppService.getOrderById(orderId);
  }

  // 특정 물류센터의 주문 조회
  @GetMapping("/warehouse/{warehouseId}")
  public List<OrderAppDTO> getOrdersByWarehouse(@PathVariable("warehouseId") String warehouseId) {
    return orderAppService.getOrdersByWarehouse(warehouseId);
  }
}
