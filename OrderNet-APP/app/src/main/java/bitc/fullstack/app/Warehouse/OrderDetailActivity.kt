package bitc.fullstack.app.Warehouse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.Branch.BranchMainActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.Warehouse.WHOrderHistory
import bitc.fullstack.app.appserver.AppServerClass
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
//    private var warehouseId = "WH_BRK" // 로그인한 물류센터
    private lateinit var warehouseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        warehouseId = intent.getStringExtra("userRefId") ?: ""

        adapter = WHOrderAppItemAdapter(emptyList())
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter

        //        홈 버튼
        val homeButton: ImageButton = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(this, WHMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        val menuButton: ImageButton = findViewById(R.id.menu)

        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.wh_header_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.menu_stock -> {
                        Toast.makeText(this, "재고현황", Toast.LENGTH_SHORT).show()
                        val warehouseId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
                        val intent = Intent(this, WHOrderHistory::class.java)
                        intent.putExtra("userRefId", warehouseId) // userRefId 전달
                        startActivity(intent)
                        true
                    }
                    R.id.btn_logout -> {
                        // 1. 저장된 값 삭제
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().clear().apply()

                        // 2. 로그인 화면으로 이동
                        val intent = Intent(this@OrderDetailActivity, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        // 3. 현재 액티비티 종료
                        finish()

                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

        // 주문 상세 목록
        selectWhOrder()

        // ✅ orderId 정확한 키 사용

        val orderId = intent.getStringExtra("order_id") ?: run {
            showToast("주문 ID를 받아오지 못했습니다.")
            finish()
            return
        }

        binding.textOrderId.text = orderId


        // 출고 버튼 클릭 리스너 추가
        binding.exportButton.setOnClickListener {
            processOutbound()
//            processOutbound(orderId, warehouseId)
        }
    }

    // 주문 상세 목록
    private fun selectWhOrder(){
        val orderId = intent.getStringExtra("order_id") ?: run {
            showToast("주문 ID를 받아오지 못했습니다.")
            finish()
            return
        }

        val api = AppServerClass.instance
        val call = api.getOrderItemsByWarehouseAndOrder(orderId, warehouseId)
        getOrderItemsByWarehouseAndOrder(call)
    }

    // 주문 상세 목록 가져오는 api
    private fun getOrderItemsByWarehouseAndOrder(call: Call<List<WHOrderAppItemDTO>>) {
        call.enqueue(object : Callback<List<WHOrderAppItemDTO>> {
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
                Log.e("WH_API", "서버 호출 실패: ${t.message}")
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

    // 출고 처리
    private fun processOutbound(){
        val orderId = intent.getStringExtra("order_id") ?: run {
            showToast("주문 ID를 받아오지 못했습니다.")
            finish()
            return
        }

        val api = AppServerClass.instance
        val call = api.processOutbound(orderId, warehouseId)
        processOutbound(call)
    }

    // 출고 처리 Retrofit 요청 처리
    private fun processOutbound(call: Call<String>) {
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val result = response.body() ?: "출고 완료"
                    showToast(result)
                    Log.d("출고 성공", "서버 응답: $result")
                    val intent = Intent(this@OrderDetailActivity, WHMainActivity::class.java)
                    startActivity(intent)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "알 수 없는 오류"
                    showToast("출고 실패 (응답 코드: ${response.code()})")
                    Log.e("출고 실패", "응답 코드: ${response.code()}, 메시지: $errorMsg")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                showToast("출고 실패 (네트워크 오류)")
                Log.e("출고 네트워크 오류", t.message ?: "에러 메시지 없음", t)
            }
        })
    }




}
