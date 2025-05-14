
package bitc.fullstack503.ordernetserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String userType;
    private String userRefId;
    private String branchSupervisor;
    private String warehouseName;
}
