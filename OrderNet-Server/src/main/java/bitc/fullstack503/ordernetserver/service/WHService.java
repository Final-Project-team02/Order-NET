package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WHDTO;

import java.util.List;
import java.util.Map;

public interface WHService {

    List<WHDTO> getMonthlyOutboundParts(String warehouseId, int month, int year);

    //    물류센터 재고조회
    List<WHDTO> selectWHComeIn(String userId);
    //    물류센터 입고 조회
    List<WHDTO> selectWHStock(String userId);

    // 출고관리
    List<WHDTO> selectWHManage(String userId);

    void updateOrderStatus(int orderItemId, String orderItemStatus, String partId, int orderItemQuantity);


    List<WHDTO> selectWHManageFiltered(String userId, String orderItemStatus, String branchName,
                                       String orderId, String orderStartDate, String orderEndDate);

}
