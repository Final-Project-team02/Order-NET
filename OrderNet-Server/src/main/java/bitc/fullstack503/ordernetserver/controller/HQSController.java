package bitc.fullstack503.ordernetserver.controller;


import bitc.fullstack503.ordernetserver.dto.HQRequestDTO;
import bitc.fullstack503.ordernetserver.dto.HQStatusDTO;
import bitc.fullstack503.ordernetserver.service.HQStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/HQstatus")
public class HQSController {

  @Autowired
  private HQStatusService hqStatusService;

  // HQ 물류센터 부품 현황 조회
  @GetMapping
  public List<HQStatusDTO> getHQStatus() throws Exception  {
    return hqStatusService.getHQStatus();
  }

// ------------------------------------- 입고 팝업
  // 입고 요청 처리
  @PostMapping("/request")
  public String createHQRequest(@RequestBody List<HQRequestDTO> requests) throws Exception {
    for (HQRequestDTO request : requests) {
      hqStatusService.createHQRequest(request);
    }
    return "입고 요청이 완료되었습니다.";
  }

  // 입고 - 물류센터 가져오기
  @GetMapping("/warehouses")
  public List<HQStatusDTO> getWarehouses() throws Exception {
    return hqStatusService.getWarehouses(); // 물류센터 목록 반환
  }

  // 입고 - 물류센터 - 부품 가져오기
  @GetMapping("/parts")
  public ResponseEntity<List<HQStatusDTO>> getParts(@RequestHeader("Warehouse-Id") String warehouseId) {
    try {
      System.out.println(warehouseId);
      // warehouseId를 통해 부품 목록 조회
      List<HQStatusDTO> parts = hqStatusService.getPartsByWarehouse(warehouseId);

      return ResponseEntity.ok(parts);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


// ------------------- 부품 등록 팝업 ----------------
  // 물류센터 카테고리 조회
  @GetMapping("/warehouse-cate")
  public List<String> getWarehouseCategories(@RequestParam String warehouseName) throws Exception {
    System.out.println("Received warehouseName: " + warehouseName);  // 값 확인용
    return hqStatusService.getWarehouseCategories(warehouseName); // 물류센터 카테고리 조회
  }

  // 부품 카테고리 조회
  @GetMapping("/part-cate")
  public List<Map<String, String>> getPartCategories(@RequestParam String warehouseId) throws Exception {
    System.out.println("Received warehouseId: " + warehouseId);  // 값 확인용
    return hqStatusService.getPartCategories(warehouseId); // 부품 카테고리 조회
  }



  // 부품 등록 - parts + WarehouseStock
  @PostMapping("/insert")
  public ResponseEntity<String> insertPart(@RequestBody HQStatusDTO hqStatusDTO) throws Exception{
    hqStatusService.insertPartAndStock(hqStatusDTO);  // 두 개의 쿼리를 호출하는 서비스 메서드
    return ResponseEntity.ok("부품 등록이 완료되었습니다.");
  }


  }


