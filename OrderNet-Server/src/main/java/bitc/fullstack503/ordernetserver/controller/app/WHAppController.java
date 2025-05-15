package bitc.fullstack503.ordernetserver.controller.app;

import bitc.fullstack503.ordernetserver.dto.PartDTO;
import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderAppDTO;
import bitc.fullstack503.ordernetserver.mapper.WHMapper;
import bitc.fullstack503.ordernetserver.service.WHService;
import bitc.fullstack503.ordernetserver.service.app.OrderAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Slf4j  // 이 어노테이션을 추가
@RestController
@RequestMapping("/app")
public class WHAppController {

  private final OrderAppService orderAppService;
  private final WHService whService;
  @Autowired
  public WHAppController(OrderAppService orderAppService, WHService whService) {
    this.orderAppService = orderAppService;
    this.whService = whService;
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

//  상품목록
@GetMapping("/parts")
public ResponseEntity<List<WHDTO>> getMonthlyOutbound(
        @RequestParam String warehouseId,
        @RequestParam int month,
        @RequestParam int year
) {

  log.info("요청 파라미터 warehouseId={}, month={}, year={}", warehouseId, month, year);
  // 실제 데이터 조회 및 리턴
  List<WHDTO> result = whService.getMonthlyOutboundParts(warehouseId, month, year);
  return ResponseEntity.ok(result);
}

}
