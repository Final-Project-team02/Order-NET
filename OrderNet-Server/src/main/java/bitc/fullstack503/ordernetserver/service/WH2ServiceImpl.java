package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WH2DTO;
import bitc.fullstack503.ordernetserver.mapper.WH2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WH2ServiceImpl implements WH2Service {

    @Autowired
    private WH2Mapper whMapper;

    //    물류센터 재고조회
//    @Override
//    public List<WHDTO> selectWHStock() {
//        return whMapper.selectWHStock();
//    }

    //    물류센터 입고조회
//    @Override
//    public List<WHDTO> selectWHComeIn() {
//        return whMapper.selectWHComeIn();
//    }



    @Override
    public List<WH2DTO> selectWHStock(String warehouseId) {
        return whMapper.selectWHStock(warehouseId);
    }

    @Override
    public List<WH2DTO> selectWHComeIn(String warehouseId) {
        return whMapper.selectWHComeIn(warehouseId);
    }

    //    재고관리 조회
    @Override
    public List<WH2DTO> selectWHManage(String userId) {
        return whMapper.selectWHManage(userId);
    }

    @Override
    public void updateOrderStatus(int orderItemId, String orderItemStatus) {
        whMapper.updateOrderStatus(orderItemId, orderItemStatus);
    }


}