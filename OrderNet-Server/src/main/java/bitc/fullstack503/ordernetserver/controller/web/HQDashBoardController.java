package bitc.fullstack503.ordernetserver.controller.web;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.NoticeDTO;
import bitc.fullstack503.ordernetserver.service.HQDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HQDashBoard")
public class HQDashBoardController {

  @Autowired
  private HQDashBoardService hqDashBoardService;

  // 대리점 정보 가져오기
  @GetMapping("/BranchPos")
  public List<ClientDTO> selectBranchInfo() {
    return hqDashBoardService.selectBranchInfo();
  }

  // 공지 조회
  @GetMapping("/HQNotice")
  public List<NoticeDTO> selectNoticeList() {
    return hqDashBoardService.selectNoticeList();
  }

  // 공지 작성
  @PostMapping("/HQNotice")
  public ResponseEntity<String> insertNotice(@RequestBody NoticeDTO noticeDTO) {
      hqDashBoardService.insertNotice(noticeDTO);
    return ResponseEntity.ok("공지 작성");
  }

  // 공지 수정
  @DeleteMapping ("/HQNotice/{noticeId}")
  public ResponseEntity<String> deleteNotice(@PathVariable("noticeId") String noticeId) {
    hqDashBoardService.deleteNotice(noticeId);
    return ResponseEntity.ok("공지 삭제");
  }


}
