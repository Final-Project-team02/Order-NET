package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

@Data
public class PartDTO {
    private String partsId;
    private String partName;
    private String partCate;
    private int quantity;
    private double partPrice;
}