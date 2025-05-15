package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.ClientDTO;
import bitc.fullstack503.ordernetserver.dto.OrderDTO;
import bitc.fullstack503.ordernetserver.dto.OrderItemDTO;
import bitc.fullstack503.ordernetserver.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientMapper clientMapper;

  @Override
  public List<ClientDTO> selectClientList() throws Exception {
    return clientMapper.selectClientList();
  }

  @Override
  public void insertclientdata(ClientDTO clientDTO) throws Exception {
    // branch_id 생성
    String city = clientDTO.getCity();
    String generatedBranchId = generateBranchId(city);
    clientDTO.setBranchId(generatedBranchId);

    // DB 저장
    clientMapper.insertclientdata(clientDTO);
    clientMapper.insertclientLogin(clientDTO);
  }

  private String generateBranchId(String city) {
    int count = clientMapper.countBranchesByCity(city);
    return city + String.format("%02d", count + 1);
  }

  @Override
  public void deletecliendata(List<String> branchIds) throws Exception {
    try {
      clientMapper.deletecliendata(branchIds);
      clientMapper.deleteClientLogin(branchIds);
    } catch (Exception e) {
      throw new Exception("대리점 삭제 실패: " + e.getMessage(), e);
    }
  }

  @Override
  public List<OrderDTO> selectClientRanking() throws Exception {
    return clientMapper.selectClientRanking();
  }

  @Override
  public List<OrderItemDTO> selectProtuctRanking() throws Exception {
    return clientMapper.selectProtuctRanking();
  }

  @Override
  public int updateClient(ClientDTO clientDTO) throws Exception {
    int result1 = clientMapper.updateClientBranch(clientDTO); // branch 테이블
    int result2 = clientMapper.updateClientLogin(clientDTO);  // user_account 테이블
    return (result1 > 0 && result2 > 0) ? 1 : 0;
  }

  @Override
  public ClientDTO selectClientByBranchId(String branchId) {
    return clientMapper.selectClientByBranchId(branchId);
  }
}
