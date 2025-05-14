package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

// 출고 재고 변경 DTO
@Data
public class WarehouseStockDTO {

    private int stockId;
    private String warehouseId;
    private String partId;
    private int stockQuantity;

}
