package bitc.fullstack.app.appserver

import bitc.fullstack.app.dto.OrderAppDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServerInterface {

  @GET("server")
  fun serverTest(): Call<String>

  @GET("warehouse/{warehouseId}")
  fun getOrdersByWarehouse(@Path("warehouseId") warehouseId: String): Call<List<OrderAppDTO>>

  @GET("orders/{orderId}/items/{warehouseId}")
  fun getOrderItemsByWarehouseAndOrder(
    @Path("orderId") orderId: String,
    @Path("warehouseId") warehouseId: String
  ): Call<List<WHOrderAppItemDTO>>

  @POST("orders/{orderId}/outbound")
  fun processOutbound(
    @Path("orderId") orderId: String,
    @Query("warehouseId") warehouseId: String
  ): Call<String>


  @GET("app/parts")
  fun getMonthlyOutboundParts(
    @Query("warehouseId") warehouseId: String,
    @Query("month") month: Int,
    @Query("year") year: Int
  ): Call<List<WHDTO>>
}