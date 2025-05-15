package bitc.fullstack.app.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class WHDTO(
    @SerializedName("orderId")
    var orderId: String = "",

    @SerializedName("orderDate")
    var orderDate: Date ,

    @SerializedName("orderDueDate")
    var orderDueDate: Date,

    @SerializedName("branchId")
    var branchId: String ="",

    @SerializedName("branchName")
    var branchName: String ="",

    @SerializedName("partId")
    var partId: String ="",

    @SerializedName("partCate")
    var partCate: String ="",

    @SerializedName("partName")
    var partName: String ="",

    @SerializedName("partPrice")
    var partPrice: Int =0,

    @SerializedName("stockQuantity")
    var stockQuantity: Int =0,

    @SerializedName("orderItemQuantity")
    var orderItemQuantity: Int =0,

    @SerializedName("totalQuantity")
    var totalQuantity: Int =0,

    @SerializedName("outboundDate")
    var outboundDate: Date,

    @SerializedName("warehouseId")
    var warehouseId: Int = 0
) : Serializable
