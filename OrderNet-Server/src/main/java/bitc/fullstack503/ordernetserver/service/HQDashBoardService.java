package bitc.fullstack503.ordernetserver.service;

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
}
