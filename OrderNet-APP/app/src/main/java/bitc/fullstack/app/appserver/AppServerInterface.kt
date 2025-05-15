package bitc.fullstack.app.appserver

import bitc.fullstack.app.dto.OrderAppDTO
import bitc.fullstack.app.dto.WHDTO
import bitc.fullstack.app.dto.WHOrderAppItemDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServerInterface {

  // 물류센터 주문목록 조회
  @GET("wh/{warehouseId}")
  fun getOrdersByWarehouse(@Path("warehouseId") warehouseId: String): Call<List<OrderAppDTO>>

  // 물류센터 주문 상세 조회
  @GET("wh/orders/{orderId}/items/{warehouseId}")
  fun getOrderItemsByWarehouseAndOrder(
    @Path("orderId") orderId: String,
    @Path("warehouseId") warehouseId: String
  ): Call<List<WHOrderAppItemDTO>>

  // 물류센터 출고
  @POST("wh/orders/{orderId}/outbound")
  fun processOutbound(
    @Path("orderId") orderId: String,
    @Query("warehouseId") warehouseId: String
  ): Call<String>

  // 물류센터 그래프
  @GET("wh/partsStock")
  fun getMonthlyOutboundParts(
    @Query("warehouseId") warehouseId: String,
    @Query("month") month: Int,
    @Query("year") year: Int
  ): Call<List<WHDTO>>
}