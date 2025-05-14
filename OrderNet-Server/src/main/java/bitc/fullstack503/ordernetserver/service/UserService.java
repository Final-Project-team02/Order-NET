
// UserService.java
package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.entity.UserAccount;


public interface UserService {
    UserAccount findByUserId(String userId);
}
