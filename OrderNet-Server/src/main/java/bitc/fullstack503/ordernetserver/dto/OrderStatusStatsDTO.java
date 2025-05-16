package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

@Data
public class OrderStatusStatsDTO {
  private int totalOrders;
  private int shippedOrders;
  private int deniedOrders;
  private int pendingOrders;
}