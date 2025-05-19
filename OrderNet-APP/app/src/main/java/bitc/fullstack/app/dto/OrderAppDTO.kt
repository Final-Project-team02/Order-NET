package bitc.fullstack.app.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class OrderAppDTO(
    @SerializedName("order_id")
    var orderId: String,

    @SerializedName("order_date")
    var orderDate: Date,

    @SerializedName("order_due_date")
    var orderDueDate: Date,

    @SerializedName("branch_id")
    var branchId: String,

    @SerializedName("branch_name")
    var branchName: String,

    @SerializedName("part_id")
    var partId: String,

    @SerializedName("part_name")
    var partName: String,

    @SerializedName("part_cate")
    var partCate: String,

    @SerializedName("order_item_quantity")
    var orderItemQuantity: Int,

    @SerializedName("order_item_price")
    var orderItemPrice: BigDecimal,

    @SerializedName("order_price")
    var orderPrice: BigDecimal,

    @SerializedName("order_item_status")
    var orderItemStatus: String,

    @SerializedName("warehouse_stock")
    var warehouseStock: Int?,

    @SerializedName("total_quantity")
    var totalQuantity: Int,

    @SerializedName("order_status")
    var orderStatus: String,

    @SerializedName("warehouse_order_price")
    var warehouseOrderPrice: BigDecimal
)
