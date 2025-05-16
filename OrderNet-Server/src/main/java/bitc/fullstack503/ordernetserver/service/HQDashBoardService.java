package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;

import java.util.List;

public interface HQDashBoardService {

  // 대리점 정보 가져오기
  List<ClientDTO> selectBranchInfo();
}
