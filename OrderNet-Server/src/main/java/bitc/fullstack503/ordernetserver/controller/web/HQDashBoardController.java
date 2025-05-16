package bitc.fullstack503.ordernetserver.controller.web;

import bitc.fullstack503.ordernetserver.service.HQDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/HQDashBoard")
public class HQDashBoardController {

  @Autowired
  private HQDashBoardService hqDashBoardService;


}
