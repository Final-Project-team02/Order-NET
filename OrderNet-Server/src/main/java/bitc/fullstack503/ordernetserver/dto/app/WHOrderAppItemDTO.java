package bitc.fullstack503.ordernetserver.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WHOrderAppItemDTO {

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("order_item_id")
  private int orderItemId;

  @JsonProperty("part_id")
  private String partId;

  @JsonProperty("part_name")
  private String partName;

  @JsonProperty("part_cate")
  private String partCate;

  @JsonProperty("part_img")
  private String partImg;

  @JsonProperty("order_item_quantity")
  private int orderItemQuantity;

  @JsonProperty("order_item_price")
  private BigDecimal orderItemPrice;

  @JsonProperty("order_item_status")
  private String orderItemStatus;

  @JsonProperty("order_date")
  private Date orderDate;

  @JsonProperty("order_due_date")
  private Date orderDueDate;

  @JsonProperty("order_status")
  private String orderStatus;

  @JsonProperty("order_price")
  private BigDecimal orderPrice;

  @JsonProperty("branch_name")
  private String branchName;

  @JsonProperty("warehouse_stock")
  private Integer warehouseStock;
}
