package bitc.fullstack.app.appserver

import bitc.fullstack.app.dto.BranchDTO
import bitc.fullstack.app.dto.OrderRequestDTO
import bitc.fullstack.app.dto.PartsDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AppServerInterface {

  @GET("server")
  fun serverTest(): Call<String>

  // 대리점 주문화면
  @GET("branch/branchOrder")
  fun BranchOrder(@Header("userId") userId: String): Call<List<BranchDTO>>

  // 대리점 상품선택
  @GET("branch/productChoose")
  fun ProductChoose(): Call<List<PartsDTO>>

  // 대리점 주문 요청
  @POST("branch/orderRequest")
  fun placeOrder(@Body orderRequest: OrderRequestDTO): Call<Void>

}