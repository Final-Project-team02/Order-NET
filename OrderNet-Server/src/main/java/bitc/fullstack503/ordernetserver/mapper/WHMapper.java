package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WHMapper {

    //    물류센터 재고조회
    List<WHDTO> selectWHStock();
    // 물류센터 입고조회
    List<WHDTO> selectWHComeIn();

}
