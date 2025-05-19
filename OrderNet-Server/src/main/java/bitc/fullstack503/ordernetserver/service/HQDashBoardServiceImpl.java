package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
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
}
