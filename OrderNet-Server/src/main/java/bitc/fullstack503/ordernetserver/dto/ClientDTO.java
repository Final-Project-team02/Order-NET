package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private String userId;
    private String userPw;
    private String branchId; // 지점 ID (자동 생성)
    private String branchName;
    private String branchSupervisor;
    private String branchPhone;
    private String branchZipCode;
    private String branchRoadAddr;
    private String branchDetailAddr;
    private String city;                  // 도시명 (부산, 서울 등)
}