package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.HQRequestDTO;
import bitc.fullstack503.ordernetserver.dto.HQStatusDTO;

import java.util.List;
import java.util.Map;

public interface HQStatusService {

  // HQ 물류센터 부품 현황 조회
  List<HQStatusDTO> getHQStatus() throws Exception ;

  // 입고 요청 처리
  void createHQRequest(HQRequestDTO request) throws Exception ;

  // 물류센터 목록 조회
  List<HQStatusDTO> getWarehouses() throws Exception;

  // 물류센터에 해당하는 부품 목록 조회
  List<HQStatusDTO> getPartsByWarehouse(String warehouseId) throws Exception;


  // 물류센터 카테고리 조회
  List<String> getWarehouseCategories(String warehouseName) throws Exception;

  // 부품 카테고리 조회
  List<Map<String, String>> getPartCategories(String warehouseId) throws Exception;


  // 부품 등록 - parts + WarehouseStock
  void insertPartAndStock(HQStatusDTO hqStatusDTO) throws Exception;

}
