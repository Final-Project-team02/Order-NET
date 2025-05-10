package bitc.fullstack503.ordernetserver.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartsAppDTO {

  @JsonProperty("partId")
  private String partId;

  @JsonProperty("partName")
  private String partName;

  @JsonProperty("partCate")
  private String partCate;

  @JsonProperty("partPrice")
  private String partPrice;

  @JsonProperty("partImg")
  private String partImg;

}
