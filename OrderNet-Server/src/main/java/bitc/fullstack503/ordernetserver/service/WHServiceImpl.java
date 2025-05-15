package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.mapper.WHMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WHServiceImpl implements WHService {

    @Autowired
    private WHMapper whMapper;

    @Override
    public List<WHDTO> getMonthlyOutboundParts(String warehouseId, int month, int year) {
        return whMapper.getMonthlyOutboundPartsByWarehouse(warehouseId, month, year);
    }

    //    물류센터 재고조회
    @Override
    public List<WHDTO> selectWHStock() {
        return whMapper.selectWHStock();
    }

    //    물류센터 입고조회
    @Override
    public List<WHDTO> selectWHComeIn() {
        return whMapper.selectWHComeIn();
    }

}
