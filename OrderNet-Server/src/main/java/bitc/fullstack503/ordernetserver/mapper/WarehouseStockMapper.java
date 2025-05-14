package bitc.fullstack503.ordernetserver.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WarehouseStockMapper {
    Integer selectStockQuantity(String partId);

//    수량 차감
    int deleteStock(@Param("partId") String partId, @Param("orderItemQuantity") int orderItemQuantity);
//    수량 복원
    int restoreStock(@Param("partId") String partId, @Param("orderItemQuantity") int orderItemQuantity);


}
