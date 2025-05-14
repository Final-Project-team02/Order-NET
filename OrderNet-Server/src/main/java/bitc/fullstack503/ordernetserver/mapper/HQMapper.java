package bitc.fullstack503.ordernetserver.mapper;
import bitc.fullstack503.ordernetserver.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HQMapper {

    //  현재 주문 개수 가져오기
    int getCurrentOrderCount();

    //    미결제 리스트
    List<UnpaidListDTO> findOrderItemsWithBranchAndParts();

    //    결제 내역
    List<FindPaymentDTO> findPayment();

    //    결제 내역 상세
    List<FindPaymentDetailDTO> findPaymentDetail();

    //    발주
    List<OrderListDTO>  orderList();

    //    조회 결과
    List<SearchDTO> selectOrderList(SearchDTO dto);

    // 단일 주문 처리
    int updateOrderStatusAndDeny(@Param("orderId") String orderId,
                                 @Param("orderStatus") String orderStatus,
                                 @Param("orderDeny") String orderDeny);

    // 복수 주문 처리
    int updateMultipleOrderStatusAndDeny(@Param("orderIdList") List<String> orderIdList,
                                         @Param("orderStatus") String orderStatus,
                                         @Param("orderDeny") String orderDeny);

}
