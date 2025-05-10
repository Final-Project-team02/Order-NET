package bitc.fullstack503.ordernetserver.service.app;

import bitc.fullstack503.ordernetserver.dto.app.BranchAppDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderItemDTO;
import bitc.fullstack503.ordernetserver.dto.app.OrderRequestDTO;
import bitc.fullstack503.ordernetserver.dto.app.PartsAppDTO;
import bitc.fullstack503.ordernetserver.mapper.app.BranchAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class BranchAppServiceImpl implements BranchAppService {

  @Autowired
  private BranchAppMapper branchAppMapper;

  // 앱 주문하기 페이지 접속 - 해당 대리점 정보 출력
  @Override
  public List<BranchAppDTO> selectBranchInfo(String userId) {
    return branchAppMapper.selectBranchInfo(userId);
  }

  // 상품 선택 - 부품 정보 가져오기
  @Override
  public List<PartsAppDTO> productChoose() {
    return branchAppMapper.productChoose();
  }

  // 대리점 주문 요청
  @Override
  public void insertOrder(OrderRequestDTO orderRequestDTO) {

    // 1. 주문번호 생성
    String formattedDate = LocalDate.parse(orderRequestDTO.getOrderDate())  // "2025-05-10" 파싱
                            .format(DateTimeFormatter.ofPattern("yyMMdd")); // → "250510"
    int todayCount = branchAppMapper.countTodayOrders(orderRequestDTO.getBranchId());
    String orderId = formattedDate + "-" + orderRequestDTO.getBranchId() + "-" + String.format("%03d", todayCount + 1);
    orderRequestDTO.setOrderId(orderId);

    // 2. 각 아이템에 order_id 주입
    if (orderRequestDTO.getItems() != null) {
      for (OrderItemDTO item : orderRequestDTO.getItems()) {
        item.setOrderId(orderId);
      }
    }

    // 주문 삽입
    branchAppMapper.insertOrder(orderRequestDTO);

    // 주문 아이디를 이용한 - 주문 항목 삽입
    // order_id는 LAST_INSERT_ID()를 통해 MyBatis에서 자동으로 처리
    branchAppMapper.insertOrderItems(orderRequestDTO.getItems());
  }
}
