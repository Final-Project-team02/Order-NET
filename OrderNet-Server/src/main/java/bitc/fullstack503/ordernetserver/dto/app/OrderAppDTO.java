package bitc.fullstack503.ordernetserver.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderAppDTO {

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("order_date")
  private Date orderDate;

  @JsonProperty("order_due_date")
  private Date orderDueDate;

  @JsonProperty("branch_id")
  private String branchId;

  @JsonProperty("branch_name")
  private String branchName;

  @JsonProperty("part_id")
  private String partId;

  @JsonProperty("part_name")
  private String partName;

  @JsonProperty("part_cate")
  private String partCate;

  @JsonProperty("order_item_quantity")
  private int orderItemQuantity;

  @JsonProperty("order_item_price")
  private BigDecimal orderItemPrice;

  @JsonProperty("order_price")
  private BigDecimal orderPrice;

  @JsonProperty("order_item_status")
  private String orderItemStatus;

  @JsonProperty("total_quantity")
  private int totalQuantity;  // 총 품목 수량

  @JsonProperty("order_status")
  private String orderStatus; // 주문 상태
}
