package bitc.fullstack503.ordernetserver.service;
import bitc.fullstack503.ordernetserver.dto.*;
import bitc.fullstack503.ordernetserver.mapper.HQMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HQServiceImpl implements HQService {

    @Autowired
    private HQMapper hqMapper;

//    미결제 리스트
    @Override
    public List<UnpaidListDTO> getOrderItemInfoList() {
        return hqMapper.findOrderItemsWithBranchAndParts();
    }

//    발주 내역
    @Override
    public List<OrderListDTO> getOrderList() {
        return hqMapper.orderList();
    }

    //    결제나 반려된 것들 리스트
    @Override
    public List<FindPaymentDTO> getFindPaymentList() {
        return hqMapper.findPayment();
    }

    //    결제, 반려 상세
    @Override
    public List<FindPaymentDetailDTO> getFindPaymentDetailList() {
        return hqMapper.findPaymentDetail();
    }

//    조회 결과
    @Override
    public List<SearchDTO> getFilteredOrders(SearchDTO dto) {
        return hqMapper.selectOrderList(dto);
    }

//    단일 주문 처리
    @Override
    public int updateOrderStatusAndDeny(String orderId,  String orderStatus, String orderDeny) {
        return hqMapper.updateOrderStatusAndDeny(orderId, orderStatus, orderDeny);
    }

//    복수 주문 처리
    @Override
    public int updateMultipleOrderStatusAndDeny(List<String> orderIdList, String orderStatus, String orderDeny) {
        return hqMapper.updateMultipleOrderStatusAndDeny(orderIdList, orderStatus, orderDeny);
    }

}
