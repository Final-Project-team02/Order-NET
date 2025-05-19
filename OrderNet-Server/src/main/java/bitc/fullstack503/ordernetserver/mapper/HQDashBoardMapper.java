package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HQDashBoardMapper {

  // 대리점 정보 가져오기
  List<ClientDTO> selectBranchInfo();
}
