package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.service.WHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("")
@RestController
public class WHController {


    @Autowired
    private WHService whService;


    //    물류센터 재고조회
    @GetMapping("/WHMain")
    public Map<String, List<WHDTO>> selectWHcheck() {
        // 입고 리스트
        List<WHDTO> comeInList = whService.selectWHComeIn();

        //  재고 리스트
        List<WHDTO> stockList = whService.selectWHStock();

        // result 변수 Map 객체 선언
        Map<String, List<WHDTO>> result = new HashMap<>();

        // map<key, value> : 1, 사과     print 1번 = 사과
        result.put("comeInList", comeInList);
        result.put("stockList", stockList);

        return result;
    }

    //    출고관리

    // 출고 상태 변경
    @PutMapping("/saveStatus")
    public ResponseEntity<String> saveAllStatus(@RequestBody List<WHDTO> updatedItems) {
        for (WHDTO dto : updatedItems) {
            whService.updateOrderStatus(dto.getOrderItemId(), dto.getOrderItemStatus());
        }
        return ResponseEntity.ok("전체 저장 완료");
    }


}
