package bitc.fullstack503.ordernetserver.controller;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.service.WHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

        Map<String, List<WHDTO>> result = new HashMap<>();
        result.put("comeInList", comeInList);
        result.put("stockList", stockList);

        return result;
    }

//    재고관리
@GetMapping("/WHManage")
@ResponseBody
public Map<String, List<WHDTO>> selectWHManage() {
    List<WHDTO> whList = whService.selectWHManage();

    Map<String, List<WHDTO>> result = new HashMap<>();
    result.put("whList", whList);

    return result; // → JSON으로 자동 변환됨
}




}
