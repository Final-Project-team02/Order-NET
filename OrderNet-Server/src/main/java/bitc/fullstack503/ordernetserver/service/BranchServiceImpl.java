package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;
import bitc.fullstack503.ordernetserver.dto.PartDTO;
import bitc.fullstack503.ordernetserver.mapper.BranchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

  private final BranchMapper branchMapper;

  @Autowired
  public BranchServiceImpl(BranchMapper branchMapper) {
    this.branchMapper = branchMapper;
  }

  @Override
  public List<OrderDTO> getOrdersByBranch(String branchId, String orderId, String startDate, String endDate) {
    // 주문번호와 날짜 범위로 필터링된 주문 목록 조회
    List<OrderDTO> orders = branchMapper.selectOrdersByBranchWithFilters(branchId, orderId, startDate, endDate);
    for (OrderDTO order : orders) {
      // 각 주문에 대한 아이템 조회
      List<OrderItemDTO> items = branchMapper.selectOrderItemsByOrderId(order.getOrderId());
      order.setOrderItems(items);
    }
    return orders;
  }

  @Override
  public List<PartDTO> getPartsByOrderId(String orderId) {
    return branchMapper.selectPartsByOrderId(orderId);
  }
}
