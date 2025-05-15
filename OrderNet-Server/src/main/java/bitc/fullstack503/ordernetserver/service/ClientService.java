package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;

import java.util.List;

public interface ClientService {
  List<ClientDTO> selectClientList() throws Exception;

  void insertclientdata(ClientDTO clientDTO) throws Exception;

  // 여러 대리점 삭제 메서드
  void deletecliendata(List<String> branchIds) throws Exception;

  //    순위
  List<OrderDTO> selectClientRanking() throws Exception;

  List<OrderItemDTO> selectProtuctRanking() throws Exception;

  int updateClient(ClientDTO clientDTO) throws Exception;

  ClientDTO selectClientByBranchId(String branchId) ;

}