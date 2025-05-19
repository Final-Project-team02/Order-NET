package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderMonthlySalesDTO {

    private String branchId;
    private String orderMonth;
    private BigDecimal totalSales;

}
