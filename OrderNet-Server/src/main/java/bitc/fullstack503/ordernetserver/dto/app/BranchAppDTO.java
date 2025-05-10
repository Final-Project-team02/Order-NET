package bitc.fullstack503.ordernetserver.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BranchAppDTO {

  @JsonProperty("branchName")
  private String branchName;

  @JsonProperty("branchPhone")
  private String branchPhone;

  @JsonProperty("branchZipCode")
  private String branchZipCode;

  @JsonProperty("branchRoadAddr")
  private String branchRoadAddr;

}
