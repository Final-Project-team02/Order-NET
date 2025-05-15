// UserAccount.java
package bitc.fullstack503.ordernetserver.entity;

import lombok.Data;

@Data
public class UserAccount {
    private String userId;
    private String userPw;
    private String userRefId;
    private String userType;

    // 추가된 필드 (JOIN 결과용)
    private String branchSupervisor;
    private String branchName;
    private String warehouseName;
}
