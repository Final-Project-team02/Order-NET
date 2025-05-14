package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WH2DTO;

import java.util.List;

public interface WH2Service {
    //    물류센터 재고조회
//    List<WHDTO> selectWHStock();
    //    물류센터 입고 조회
//    List<WHDTO> selectWHComeIn();

    List<WH2DTO> selectWHStock(String warehouseId);
    List<WH2DTO> selectWHComeIn(String warehouseId);


    List<WH2DTO> selectWHManage(String userId);


    public void updateOrderStatus(int orderItemId, String orderItemStatus);

}
