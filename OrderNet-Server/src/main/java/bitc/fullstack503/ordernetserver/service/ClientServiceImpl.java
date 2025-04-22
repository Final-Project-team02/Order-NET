package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientMapper clientMapper;
}
