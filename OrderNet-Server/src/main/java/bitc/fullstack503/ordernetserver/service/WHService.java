package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WHDTO;

import java.util.List;

public interface WHService {
    //    물류센터 재고조회
    List<WHDTO> selectWHStock();

    //    물류센터 입고 조회
    List<WHDTO> selectWHComeIn();

    List<WHDTO> selectWHManage(String userId);


    public void updateOrderStatus(int orderItemId, String orderItemStatus);

}
