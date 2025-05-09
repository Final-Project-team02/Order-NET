package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.PartDTO;

import java.util.List;

public interface BranchService {
    // 대리점 주문 목록 조회
    List<OrderDTO> getOrdersByBranch(String branchId, String orderId, String startDate, String endDate);

    List<PartDTO> getPartsByOrderId(String orderId);
}