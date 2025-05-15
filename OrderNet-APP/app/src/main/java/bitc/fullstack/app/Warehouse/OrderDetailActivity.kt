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
import bitc.fullstack.app.dto.OrderAppDTO
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailBinding by lazy {
        ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private lateinit var apiService: AppServerInterface
    private lateinit var adapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Window insets 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val orderItems = listOf(
            OrderItem("코드1", "파트A", "파트B"),
            OrderItem("코드2", "파트C", "파트D"),
            OrderItem("코드3", "파트E", "파트F")
        )

        // RecyclerView 설정
        adapter = OrderAdapter(orderItems)
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter


        // Retrofit 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.100.203.89:8080/app/") // 서버 주소 확인 필요
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(AppServerInterface::class.java)


        // 서버에서 주문 목록 가져오기
        getOrdersByWarehouse("WH_BRK") // warehouseId는 필요에 따라 인텐트로 받을 수도 있음
    }

    private fun getOrdersByWarehouse(warehouseId: String) {
        val call = apiService.getOrdersByWarehouse(warehouseId)
        call.enqueue(object : Callback<List<OrderAppDTO>> {
            override fun onResponse(
                call: Call<List<OrderAppDTO>>,
                response: Response<List<OrderAppDTO>>
            ) {
                if (response.isSuccessful) {
                    val orders = response.body() ?: emptyList()
                    val order = orders.firstOrNull()

                    if (order != null) {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val orderDateStr = dateFormat.format(order.orderDate)
                        val deliveryDateStr = order.orderDueDate?.let { dateFormat.format(it) } ?: "N/A"

                        binding.dtStartData.text = orderDateStr
                        binding.dtEndData.text = deliveryDateStr
                        binding.status.text = order.orderItemStatus ?: "상태 미정"
                        binding.branchName1.text = order.branchName
                    }
                } else {
                    Toast.makeText(
                        this@OrderDetailActivity,
                        "서버 오류: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<OrderAppDTO>>, t: Throwable) {
                Toast.makeText(
                    this@OrderDetailActivity,
                    "네트워크 오류: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
