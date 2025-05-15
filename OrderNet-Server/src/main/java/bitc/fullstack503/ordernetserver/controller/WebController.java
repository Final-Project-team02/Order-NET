package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;
import bitc.fullstack503.ordernetserver.service.ClientService;
import bitc.fullstack503.ordernetserver.service.HQService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("")
  public String test() {
    return "test";
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




}
