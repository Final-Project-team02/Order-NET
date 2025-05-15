package bitc.fullstack.app.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class WHOrderAppItemDTO(

    @SerializedName("order_id")
    var orderId: String,

    @SerializedName("order_item_id")
    var orderItemId: Int,

    @SerializedName("part_id")
    var partId: String,

    @SerializedName("part_name")
    var partName: String,

    @SerializedName("part_cate")
    var partCate: String,

    @SerializedName("part_img")
    var partImg: String,

    @SerializedName("order_item_quantity")
    var orderItemQuantity: Int,

    @SerializedName("order_item_price")
    var orderItemPrice: BigDecimal,

    @SerializedName("order_item_status")
    var orderItemStatus: String,

    @SerializedName("order_date")
    var orderDate: Date,

    @SerializedName("order_due_date")
    var orderDueDate: Date,

    @SerializedName("order_status")
    var orderStatus: String,

    @SerializedName("order_price")
    var orderPrice: BigDecimal,

    @SerializedName("branch_name")
    var branchName: String,


    )
