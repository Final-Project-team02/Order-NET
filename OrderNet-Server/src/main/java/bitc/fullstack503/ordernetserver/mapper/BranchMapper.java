package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;
import bitc.fullstack503.ordernetserver.dto.PartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface BranchMapper {

    // 대리점 주문 목록 조회 (필터링 추가)
    List<OrderDTO> selectOrdersByBranchWithFilters(
            @Param("branchId") String branchId,
            @Param("orderId") String orderId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

//    // 대리점 주문 목록 조회
//    List<OrderDTO> selectOrdersByBranch(String branchId);

    // 주문 상세 목록 조회
    List<OrderItemDTO> selectOrderItemsByOrderId(String orderId);

    List<PartDTO> selectPartsByOrderId(String orderId);
}
