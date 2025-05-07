package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.mapper.WHMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WHServiceImpl implements WHService {

    @Autowired
    private WHMapper whMapper;

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

//    재고관리 조회
    @Override
    public List<WHDTO> selectWHManage() {
        return whMapper.selectWHManage();
    }

}
