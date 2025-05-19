package bitc.fullstack503.ordernetserver.controller.web;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.service.HQDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
