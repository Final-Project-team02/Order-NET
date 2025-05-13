package bitc.fullstack503.ordernetserver.mapper.app;

import bitc.fullstack503.ordernetserver.dto.app.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BranchAppMapper {

  // 대리점 로그인 첫 화면 - 주문 상태 가져오기
  BranchCountDTO getBranchInfo(String branchId);

  // 앱 주문하기 페이지 접속 - 해당 대리점 정보 출력
  List<BranchAppDTO> selectBranchInfo(String userId);

  // 상품 선택 - 부품 정보 가져오기
  List<PartsAppDTO> productChoose();

  // 대리점 해당 날짜 주문 수 가져오기
  int countTodayOrders(String branchId);

  // 대리점 주문 요청
  void insertOrder(OrderRequestDTO orderRequestDTO);

  // 대리점 주문 항목 삽입
  void insertOrderItems(List<OrderItemDTO> items);

  // 대리점 주문 내역
  List<BranchOrderDTO> getOrderHistory(Map<String, Object> paramMap);

  // 대리점 주문 가져오기
  BranchOrderDTO getOrderDetail(String orderNumber);

  // 주문 상세 item 가져오기
  List<PartsDetailAppDTO> getOrderParts(String orderNumber);
}
