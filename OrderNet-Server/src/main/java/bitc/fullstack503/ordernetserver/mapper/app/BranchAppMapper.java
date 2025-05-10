package bitc.fullstack503.ordernetserver.mapper.app;

import bitc.fullstack503.ordernetserver.dto.app.BranchAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderItemDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderRequestDTO;
import bitc.fullstack503.ordernetserver.dto.app.PartsAppDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BranchAppMapper {

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

}
