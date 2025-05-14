package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.WH2DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WH2Mapper {

    //    물류센터 재고조회
//    List<WHDTO> selectWHStock();

    // 물류센터 입고조회
//    List<WHDTO> selectWHComeIn();


    List<WH2DTO> selectWHStock(String warehouseId);

    // 물류센터 입고조회
    List<WH2DTO> selectWHComeIn(String warehouseId);

    //    재고관리 조회
    List<WH2DTO> selectWHManage(String userId);


    void updateOrderStatus(int orderItemId, String orderItemStatus);
}
