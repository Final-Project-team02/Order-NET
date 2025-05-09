package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.HQRequestDTO;
import bitc.fullstack503.ordernetserver.dto.HQStatusDTO;
import bitc.fullstack503.ordernetserver.mapper.HQStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HQStatusServiceImpl implements HQStatusService {

  @Autowired
  private HQStatusMapper hqStatusMapper;

  // HQ 물류센터 부품 현황 조회
  @Override
  public List<HQStatusDTO> getHQStatus() throws Exception {
    return hqStatusMapper.selectHQStatus() ;  // MyBatis Mapper 호출
  }

  // 입고 요청 처리
  @Override
  public void createHQRequest(HQRequestDTO request) throws Exception {
    hqStatusMapper.insertHQRequest(request);
  }

  // 물류센터 목록 조회
  @Override
  public List<HQStatusDTO> getWarehouses() throws Exception {
    return hqStatusMapper.selectWarehouses(); // 물류센터 목록 반환
  }

  // 물류센터에 해당하는 부품 목록 조회
  @Override
  public List<HQStatusDTO> getPartsByWarehouse(String warehouseId) throws Exception {
    return hqStatusMapper.selectPartsByWarehouse(warehouseId); // 부품 목록 반환
  }


  @Override
  public List<String> getWarehouseCategories(String warehouseName) throws Exception{
    return hqStatusMapper.selectWHCate(warehouseName);
  }

  @Override
  public List<String> getPartCategories( String warehouseId) throws Exception{
    return hqStatusMapper.selectPartCate(warehouseId);
  }



  @Override
  public void insertPartAndStock(HQStatusDTO hqStatusDTO) throws Exception {

  }


}
