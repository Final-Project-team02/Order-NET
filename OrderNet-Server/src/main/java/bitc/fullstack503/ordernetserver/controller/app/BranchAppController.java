package bitc.fullstack503.ordernetserver.controller.app;

import bitc.fullstack503.ordernetserver.dto.app.*;
import bitc.fullstack503.ordernetserver.service.app.BranchAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/app/branch")
@RestController
public class BranchAppController {

  @Autowired
  private BranchAppService branchAppService;

  // 대리점 로그인 첫 화면
  @GetMapping("")
  public BranchCountDTO getBranchInfo(@RequestHeader("branchId") String branchId) {
    return branchAppService.getBranchInfo(branchId);
  }

  // 앱 주문하기 페이지 접속 - 해당 대리점 정보 출력
  @GetMapping("/branchOrder")
  private List<BranchAppDTO> branchOrder(@RequestHeader("userId") String userId) {

    List<BranchAppDTO> branchInfo = branchAppService.selectBranchInfo(userId);

    return branchInfo;
  }

  // 상품 선택 - 부품 정보 가져오기
  @GetMapping("/productChoose")
  private List<PartsAppDTO> productChoose() {

    List<PartsAppDTO> partsInfo = branchAppService.productChoose();

    return partsInfo;
  }

  // 선택한 상품 주문 요청
  @PostMapping("/orderRequest")
  private void insertOrder(@RequestBody OrderRequestDTO orderRequestDTO){

    System.out.println("주문 요청 수신:");
    System.out.println("주문날짜: " + orderRequestDTO.getOrderDate());
    System.out.println("납품 기한: " + orderRequestDTO.getOrderDueDate());
    System.out.println("총 가격: " + orderRequestDTO.getOrderPrice());
    System.out.println("지점 ID: " + orderRequestDTO.getBranchId());

    System.out.println(" 주문 항목들:");
    if (orderRequestDTO.getItems() != null) {
      for (OrderItemDTO item : orderRequestDTO.getItems()) {
        System.out.println("  선택 부품 : " + item.getPartId());
        System.out.println("  부품 수량 : " + item.getOrderItemQuantity());
        System.out.println("  부품 가격 : " + item.getOrderItemPrice());
      }
    } else {
      System.out.println("주문한 아이템 없음");
    }

    // 대리점 주문 요청
    branchAppService.insertOrder(orderRequestDTO);
  }

  // 대리점 주문내역
  @GetMapping("/orderHistory")
  public List<BranchOrderDTO> orderHistory(
      @RequestParam("branch_id") String branchId,
      @RequestParam("order_status") String orderStatus,
      @RequestParam(value = "start_date", required = false) String startDate,
      @RequestParam(value = "end_date", required = false) String endDate,
      @RequestParam(value = "order_id", required = false) String orderId) {

    return branchAppService.getOrderHistory(branchId, orderStatus, startDate, endDate, orderId);
  }

  // 대리점 주문 상세 내역
  @GetMapping("/orderDetail")
  public BranchOrderDTO getOrderDetail(@RequestParam("orderNumber") String orderNumber) {
    return branchAppService.getOrderDetail(orderNumber);
  }
}
