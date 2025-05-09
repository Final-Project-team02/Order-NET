package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.HQRequestDTO;
import bitc.fullstack503.ordernetserver.dto.HQStatusDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HQStatusMapper {

  // HQ 물류센터 부품 현황 조회
  List<HQStatusDTO> selectHQStatus() ;

  // 입고 요청 처리
  void insertHQRequest(HQRequestDTO request) ;

  // 물류센터 목록 조회
  List<HQStatusDTO> selectWarehouses();

  // 물류센터에 해당하는 부품 목록 조회
  List<HQStatusDTO> selectPartsByWarehouse(String warehouseId);



  // 물류센터 카테고리 조회
  List<String> selectWHCate(@Param("warehouseName") String warehouseName);

  // 부품 카테고리 조회
  List<String> selectPartCate(@Param("warehouseId") String warehouseId);

  // 부품 등록
  void insertPart(HQStatusDTO hqStatusDTO);
  void insertWarehouseStock(HQStatusDTO hqStatusDTO);
}
