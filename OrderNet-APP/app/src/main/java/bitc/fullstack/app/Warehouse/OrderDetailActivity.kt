package bitc.fullstack.app.Warehouse

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.appserver.AppServerInterface
import bitc.fullstack.app.databinding.ActivityOrderDetailBinding
import bitc.fullstack.app.dto.WHOrderAppItemDTO
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailBinding by lazy {
        ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private lateinit var apiService: AppServerInterface
    private lateinit var adapter: WHOrderAppItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = WHOrderAppItemAdapter(emptyList())
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.100.203.54:8080/app/") // 서버 주소
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(AppServerInterface::class.java)

        // ✅ orderId 정확한 키 사용
        val orderId = intent.getStringExtra("order_id") ?: run {
            showToast("주문 ID를 받아오지 못했습니다.")
            finish()
            return
        }

        val warehouseId = "WH_BRK"  // 예시용 값
        binding.textOrderId.text = orderId

        getOrderItemsByWarehouseAndOrder(orderId, warehouseId)

        // 출고 버튼 클릭 리스너 추가
        binding.exportButton.setOnClickListener {
            processOutbound(orderId, warehouseId)
        }
    }

    private fun getOrderItemsByWarehouseAndOrder(orderId: String, warehouseId: String) {
        apiService.getOrderItemsByWarehouseAndOrder(orderId, warehouseId)
            .enqueue(object : Callback<List<WHOrderAppItemDTO>> {
                override fun onResponse(
                    call: Call<List<WHOrderAppItemDTO>>,
                    response: Response<List<WHOrderAppItemDTO>>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        if (!items.isNullOrEmpty()) {
                            adapter.updateItems(items)
                            updateOrderDetails(items)
                        } else {
                            Log.e("OrderDetailActivity", "받은 데이터가 비어 있음: ${response.body()}")
                            showToast("받은 데이터가 없습니다.")
                        }
                    } else {
                        showToast("데이터를 가져오는 데 실패했습니다: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<WHOrderAppItemDTO>>, t: Throwable) {
                    showToast("네트워크 오류: ${t.message}")
                }
            })
    }

    private fun updateOrderDetails(orderItems: List<WHOrderAppItemDTO>) {
        val firstItem = orderItems.firstOrNull()
        firstItem?.let {
            binding.branchName1.text = it.branchName ?: "지점 미정"
            binding.dtStartData.text = formatDate(it.orderDate)
            binding.dtEndData.text = formatDate(it.orderDueDate)
            binding.status.text = it.orderItemStatus ?: "상태 미정"
        }

        val totalAmount = orderItems.sumOf {
            (it.orderItemPrice ?: BigDecimal.ZERO).multiply(BigDecimal(it.orderItemQuantity))
        }
        val itemCount = orderItems.sumOf { it.orderItemQuantity }

        binding.textTotalAmount.text = "총 금액: ${totalAmount}원"
        binding.textTotalCount.text = "건수: ${itemCount}개"
    }

    private fun formatDate(date: Date?): String {
        return try {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
            if (date != null) sdf.format(date) else "날짜 없음"
        } catch (e: Exception) {
            "날짜 변환 실패"
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun processOutbound(orderId: String, warehouseId: String) {
        apiService.processOutbound(orderId, warehouseId)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        showToast(response.body() ?: "출고 완료")
                        Log.d("출고 성공", "서버 응답: ${response.body()}")


                        finish()
                    } else {
                        showToast("출고 처리 중 오류가 발생했습니다 (응답 코드: ${response.code()})")
                        Log.e("출고 실패", "응답 코드: ${response.code()}, 메시지: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    showToast("출고 처리 중 오류가 발생했습니다 (네트워크 실패)")
                    Log.e("출고 네트워크 오류", t.message ?: "에러 메시지 없음")
                }
            })
    }




}
