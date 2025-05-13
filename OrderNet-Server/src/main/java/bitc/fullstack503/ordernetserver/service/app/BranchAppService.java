package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.*;

import java.util.List;
import java.util.Map;

public interface BranchAppService {

  // 대리점 로그인 첫 화면 - 주문 상태 가져오기
  BranchCountDTO getBranchInfo(String branchId);

  // 앱 주문하기 페이지 접속 - 해당 대리점 정보 출력
  List<BranchAppDTO> selectBranchInfo(String userId);

  // 상품 선택 - 부품 정보 가져오기
  List<PartsAppDTO> productChoose();

  // 대리점 주문 요청
  void insertOrder(OrderRequestDTO orderRequestDTO);

  // 대리점 주문 내역 (필터링된 주문 내역 조회)
  List<BranchOrderDTO> getOrderHistory(String branchId, String orderStatus, String startDate, String endDate, String orderId);

  // 대리점 주문 상세 내역
  BranchOrderDTO getOrderDetail(String orderNumber);
}
