package bitc.fullstack.app.appserver

import bitc.fullstack.app.dto.OrderAppDTO
import bitc.fullstack.app.dto.WHDTO
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

  @GET("app/parts")
  fun getMonthlyOutboundParts(
    @Query("warehouseId") warehouseId: String,
    @Query("month") month: Int,
    @Query("year") year: Int
  ): Call<List<WHDTO>>
}