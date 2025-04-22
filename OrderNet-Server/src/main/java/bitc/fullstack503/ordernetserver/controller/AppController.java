package bitc.fullstack503.ordernetserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mobile")
@RestController
public class AppController {

  @GetMapping("server")
  public String test() {
    return "test";
  }
}
