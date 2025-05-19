package bitc.fullstack503.ordernetserver.service;


import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;
import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.NoticeDTO;
import bitc.fullstack503.ordernetserver.mapper.HQDashBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HQDashBoardServiceImpl implements HQDashBoardService {

  @Autowired
  private HQDashBoardMapper hqDashBoardMapper;

  // 대리점 정보 가져오기
  @Override
  public List<ClientDTO> selectBranchInfo() {
    return hqDashBoardMapper.selectBranchInfo();
  }

  // 공지 조회
  @Override
  public List<NoticeDTO> selectNoticeList() {
    return hqDashBoardMapper.selectNoticeList();
  }

  // 공지 작성
  @Override
  public void insertNotice(NoticeDTO noticeDTO) {
    hqDashBoardMapper.insertNotice(noticeDTO);
  }

  // 공지 삭제
  @Override
  public void deleteNotice(String noticeId) {
    hqDashBoardMapper.deleteNotice(noticeId);
  }

    // 주문현황
    @Override
    public OrderStatusStatsDTO getOrderStatusStats() {
        return hqDashBoardMapper.selectOrderStatusStats();
    }

    //  대리점별 월간 매출 추이 그래프
    @Override
    public List<OrderMonthlySalesDTO> getMonthlySalesByBranch() {
        return hqDashBoardMapper.getMonthlySalesByBranch();
    }

}
