// UserServiceImpl.java
package bitc.fullstack503.ordernetserver.service;


import bitc.fullstack503.ordernetserver.entity.UserAccount;
import bitc.fullstack503.ordernetserver.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserAccount findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }
}
