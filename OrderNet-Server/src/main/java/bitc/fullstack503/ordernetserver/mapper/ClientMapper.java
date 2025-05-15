package bitc.fullstack503.ordernetserver.mapper;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper {
  List<ClientDTO> selectClientList();

  void insertclientdata(ClientDTO clientDTO);

  void insertclientLogin(ClientDTO clientDTO);

  int countBranchesByCity(String city);

  void deletecliendata(@Param("list") List<String> branchIds );

  void deleteClientLogin(List<String> branchIds);

  //    순위
  List<OrderDTO> selectClientRanking();

  List<OrderItemDTO> selectProtuctRanking();

  int updateClientBranch(ClientDTO clientDTO);
  int updateClientLogin(ClientDTO clientDTO);

  ClientDTO selectClientByBranchId(String branchId);
}
