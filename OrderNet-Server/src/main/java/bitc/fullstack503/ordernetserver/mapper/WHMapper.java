package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WHMapper {
    //Mapper는 추상 메서드로 선언만 해야 함
    List<WHDTO> getMonthlyOutboundPartsByWarehouse(
            @Param("warehouseId") String warehouseId,
            @Param("month") int month,
            @Param("year") int year
    );

    //    물류센터 재고조회
    List<WHDTO> selectWHStock();
    // 물류센터 입고조회
    List<WHDTO> selectWHComeIn();

}
