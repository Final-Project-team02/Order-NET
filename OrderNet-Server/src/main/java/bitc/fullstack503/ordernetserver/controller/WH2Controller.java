package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.WH2DTO;
import bitc.fullstack503.ordernetserver.service.WH2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("")
@RestController
public class WH2Controller {


    @Autowired
    private WH2Service whService;


    //    물류센터 재고조회
    @GetMapping("/WHMain")
    public Map<String, List<WH2DTO>> selectWHcheck(@RequestParam("warehouseId") String warehouseId) {
        // 입고 리스트
        List<WH2DTO> comeInList = whService.selectWHComeIn(warehouseId);

        //  재고 리스트
        List<WH2DTO> stockList = whService.selectWHStock(warehouseId);

        System.out.println("Received search params: " + warehouseId);

        // result 변수 Map 객체 선언
        Map<String, List<WH2DTO>> result = new HashMap<>();

        // map<key, value> : 1, 사과     print 1번 = 사과
        result.put("comeInList", comeInList);
        result.put("stockList", stockList);

        return result;
    }

    //    출고관리

    // 출고 상태 변경
    @PutMapping("/saveStatus")
    public ResponseEntity<String> saveAllStatus(@RequestBody List<WH2DTO> updatedItems) {
        for (WH2DTO dto : updatedItems) {
            whService.updateOrderStatus(dto.getOrderItemId(), dto.getOrderItemStatus());
        }
        return ResponseEntity.ok("전체 저장 완료");
    }


}
