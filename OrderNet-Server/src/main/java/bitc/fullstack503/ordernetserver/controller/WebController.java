package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.*;
import bitc.fullstack503.ordernetserver.service.ClientService;
import bitc.fullstack503.ordernetserver.service.HQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/HQMain")
public class WebController {

    @Autowired
    private HQService hqService;

    @Autowired
    private ClientService clientService;

    // 새로 들어온 주문 건수
    private int newOrderCount = 0;
    // 지난 주문 건수
    private int lastOrderCount = 0;

    @GetMapping("")
    public String test() {
        return "test";
    }

    // 서버 시간 반환
    @GetMapping("/server-time")
    public Map<String, String> getServerTime() {
        ZonedDateTime nowKST = ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul"));

        // ISO 8601 포맷 (예: "2025-05-14T13:45:00+09:00")
        String isoTime = nowKST.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Map<String, String> response = new HashMap<>();
        response.put("time", isoTime);

        return response;
    }

  //DB에 있는 데이터를 가져오기(대리점 정보)
  @GetMapping("/clientlist")
  public Object selectClientList() throws Exception {
    List<ClientDTO> selectclient = clientService.selectClientList();
    System.out.println("dfsdfsdfsdfsdfsdf");
    return selectclient;
  }

//  대리점 등록하기

  @PostMapping("/insertclient")
  public String insertClient(@RequestBody ClientDTO clientDTO) throws Exception {
    clientService.insertclientdata(clientDTO);  // branch_id 생성 포함
    return "등록이 잘 되었습니다";
  }

  // HQMainController 또는 WebController.java 안에 추가
  @GetMapping("/clientupdate/{branchId}")
  public ResponseEntity<ClientDTO> getClientByBranchId(@PathVariable String branchId) {
    ClientDTO client = clientService.selectClientByBranchId(branchId);
    if (client != null) {
      return ResponseEntity.ok(client);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  //  대리점 수정
  @PutMapping("/clientupdate")
  public ResponseEntity<String> updateClient(@RequestBody ClientDTO clientDTO) throws Exception {
    int result = clientService.updateClient(clientDTO);
    if(result > 0) {
      return ResponseEntity.ok("update ok");
    }
    else {
      return ResponseEntity.ok("update fail");
    }
  }

//  대리점 삭제하기

  @DeleteMapping("/deleteclients")
  public ResponseEntity<String> deleteClients(@RequestBody Map<String, List<String>> body) {
    List<String> branchIds = body.get("branchIds");

    if (branchIds == null || branchIds.isEmpty()) {
      return ResponseEntity.badRequest().body("삭제할 대리점이 없습니다.");
    }

    try {
      clientService.deletecliendata(branchIds);
      return ResponseEntity.ok("대리점 삭제 완료");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("대리점 삭제 중 오류 발생");
    }
  }

  //  대리점 중에서 지금까지 주문 금액이 가장 많은 순위로 만들기
  @GetMapping("/clientranking")
  public Object selectClientRanking() throws Exception {
    List<OrderDTO> selectclientranking = clientService.selectClientRanking();
    System.out.println("정상");
    return selectclientranking;
  }

// 마지막 달(월)에 가장 많이 팔렸던 제품 5까지 보여주기

  @GetMapping("/productranking")
  public Object selectProtuctRanking() throws Exception {
    List<OrderItemDTO> selectproductranking = clientService.selectProtuctRanking();
    System.out.println("정상");
    return selectproductranking;
  }

    // 새로운 주문 건수 조회
    @GetMapping("/orderCount")
    public int checkNewOrders() {
        // 현재 주문건수
        int currentOrderCount = hqService.getCurrentOrderCount();
        // 현재 주문 건수 - 이전 주문건수
        newOrderCount = currentOrderCount - lastOrderCount;
        // 이전 주문건수 현재 주문 건수로 최신화
        lastOrderCount = currentOrderCount;

        if (newOrderCount > 0) {
            // 새로운 주문이 있으면 서버 로그에 출력
            System.out.println("새로운 주문이 " + newOrderCount + "건 들어왔습니다.");
        }
        return newOrderCount;
    }

    // 미결제 리스트
    @GetMapping("/order-item-info")
    public List<UnpaidListDTO> getOrderItemInfoList() {
        return hqService.getOrderItemInfoList();
    }

//    발주 내역
    @GetMapping("/orderList")
    public List<OrderListDTO> getOrderList() {
        return hqService.getOrderList();
    }

    //    결제 확인
    @GetMapping("/payment")
    public List<FindPaymentDTO> getFindPaymentList() {
        return hqService.getFindPaymentList();
    }

    //    결제 or 반려 상세
    @GetMapping("/paymentDetail")
    public List<FindPaymentDetailDTO> getFindPaymentDetailList() { return hqService.getFindPaymentDetailList();}

    //    검색
    @PostMapping("/search")
    public List<SearchDTO> searchOrders(@RequestBody SearchDTO dto) {
        System.out.println("Received search params: " + dto);
        return hqService.getFilteredOrders(dto);
    }

    // 단일 , 복수 주문 처리
    @PostMapping("/update")
    public ResponseEntity<?> updateOrders(@RequestBody UpdateOrdersRequest request) {
        if (request.getOrderIdList() != null && !request.getOrderIdList().isEmpty()) {
            // 복수 처리
            int count = hqService.updateMultipleOrderStatusAndDeny(
                    request.getOrderIdList(),
                    request.getOrderStatus(),
                    request.getOrderDeny()
            );
            return ResponseEntity.ok(count + "건 처리 완료");
        } else if (request.getOrderId() != null) {
            // 단일 처리
            int result = hqService.updateOrderStatusAndDeny(
                    request.getOrderId(),
                    request.getOrderStatus(),
                    request.getOrderDeny()
            );
            return ResponseEntity.ok("1건 처리 완료");
        } else {
            return ResponseEntity.badRequest().body("주문 정보가 없습니다.");
        }
    }

}