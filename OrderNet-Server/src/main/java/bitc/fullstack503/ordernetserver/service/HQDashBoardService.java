package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.OrderMonthlySalesDTO;
import bitc.fullstack503.ordernetserver.dto.OrderStatusStatsDTO;


import java.util.List;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.NoticeDTO;

import java.util.List;

public interface HQDashBoardService {

  // 대리점 정보 가져오기
  List<ClientDTO> selectBranchInfo();

  // 공지 조회
  List<NoticeDTO> selectNoticeList();

  // 공지 작성
  void insertNotice(NoticeDTO noticeDTO);

  // 공지 삭제
  void deleteNotice(String noticeId);
    //주문현황
    OrderStatusStatsDTO getOrderStatusStats();

    //  대리점별 월간 매출 추이 그래프
    List<OrderMonthlySalesDTO> getMonthlySalesByBranch();

}
