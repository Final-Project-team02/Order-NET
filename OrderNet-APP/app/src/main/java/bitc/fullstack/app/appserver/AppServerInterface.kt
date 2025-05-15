package bitc.fullstack.app.appserver

import bitc.fullstack.app.dto.BranchCountDTO
import bitc.fullstack.app.dto.BranchDTO
import bitc.fullstack.app.dto.BranchOrderDTO
import bitc.fullstack.app.dto.OrderRequestDTO
import bitc.fullstack.app.dto.PartsDTO
import bitc.fullstack.app.dto.OrderAppDTO
import bitc.fullstack.app.dto.WHDTO
import bitc.fullstack.app.dto.WHOrderAppItemDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServerInterface {

  // 대리점 로그인 첫 화면
  @GET("branch")
  fun BranchInfo(@Header("branchId") branchId: String): Call<BranchCountDTO>

  // 대리점 주문화면
  @GET("branch/branchOrder")
  fun BranchOrder(@Header("userId") userId: String): Call<List<BranchDTO>>

  // 대리점 상품선택
  @GET("branch/productChoose")
  fun ProductChoose(@Query("category") category: String?): Call<List<PartsDTO>>

  // 대리점 주문 요청
  @POST("branch/orderRequest")
  fun placeOrder(@Body orderRequest: OrderRequestDTO): Call<Void>

  // 대리점 주문 내역
  // 대리점 주문 내역
  @GET("branch/orderHistory")
  fun orderHistory(
    @Query("branch_id") branchId: String,
    @Query("order_status") orderStatus: String?,
    @Query("start_date") startDate: String?,
    @Query("end_date") endDate: String?,
    @Query("order_id") orderId: String?
  ): Call<List<BranchOrderDTO>>


  // 대리점 주문 상세 내역
  @GET("branch/orderDetail")
  fun selectOrderDetail(
    @Query("orderNumber") orderNumber: String
  ): Call<BranchOrderDTO>

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