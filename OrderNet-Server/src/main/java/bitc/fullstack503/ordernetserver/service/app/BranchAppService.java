package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.BranchAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderRequestDTO;
import bitc.fullstack503.ordernetserver.dto.app.PartsAppDTO;

import java.util.List;

public interface BranchAppService {

  // 앱 주문하기 페이지 접속 - 해당 대리점 정보 출력
  List<BranchAppDTO> selectBranchInfo(String userId);

  // 상품 선택 - 부품 정보 가져오기
  List<PartsAppDTO> productChoose();

  // 대리점 주문 요청
  void insertOrder(OrderRequestDTO orderRequestDTO);
}
