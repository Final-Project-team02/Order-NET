package bitc.fullstack.app.Branch

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
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityOrderItemListBinding
import bitc.fullstack.app.dto.BranchOrderDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderItemListActivity : AppCompatActivity() {

    private lateinit var userRefId: String

    private val binding: ActivityOrderItemListBinding by lazy {
        ActivityOrderItemListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userRefId = intent.getStringExtra("userRefId") ?: ""

        val menuButton: ImageButton = findViewById(R.id.menu)


        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.branch_header_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_order -> {
                        Toast.makeText(this, "주문 하기", Toast.LENGTH_SHORT).show()
                        val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
                        val intent = Intent(this, BranchOrderResiActivity::class.java)
                        intent.putExtra("userRefId", branchId) // userRefId 전달
                        startActivity(intent)
                        true
                    }
                    R.id.menu_stock -> {
                        Toast.makeText(this, "주문 현황", Toast.LENGTH_SHORT).show()
                        val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
                        val intent = Intent(this, OrderHistoryActivity::class.java)
                        intent.putExtra("userRefId", branchId) // userRefId 전달
                        startActivity(intent)
                        true
                    }
                    R.id.btn_logout -> {
                        // 1. 저장된 값 삭제
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().clear().apply()

                        // 2. 로그인 화면으로 이동
                        val intent = Intent(this@OrderItemListActivity, Login::class.java)
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

        // 주문번호 받아오기
        val orderNumber = intent.getStringExtra("orderNumber") ?: ""

        // 주문상세내역
        selectOrderDetail(orderNumber);

        // 확인 버튼
        binding.confirmButton.setOnClickListener {
            finish()
        }
    }

    private fun selectOrderDetail(orderNumber : String){
        val api = AppServerClass.instance
        val call = api.selectOrderDetail(orderNumber)
        retrofitResponse(call)
    }

    private fun retrofitResponse(call: Call<BranchOrderDTO>) {
        call.enqueue(object : Callback<BranchOrderDTO> {
            override fun onResponse(p0: Call<BranchOrderDTO>, res: Response<BranchOrderDTO>) {
                if (res.isSuccessful) {

                    val orderDetail = res.body()
                    orderDetail?.let {
                        Log.d("OrderDetail", "Order ID: ${it.orderId}")
                        Log.d("OrderDetail", "Order Date: ${it.orderDate}")
                        Log.d("OrderDetail", "Order Due Date: ${it.orderDueDate}")
                        Log.d("OrderDetail", "Order Status: ${it.orderStatus}")
                        binding.orderNumberText.text = it.orderId
                        binding.orderDateText.text = it.orderDate
                        binding.deliveryDateText.text = it.orderDueDate
                        binding.statusText.text = it.orderStatus

                        // RecyclerView 어댑터에 데이터 전달
                        val adapter = OrderItemAdapter(it.partsList.values.toList())
                        binding.productListRecycler.layoutManager = LinearLayoutManager(this@OrderItemListActivity)
                        binding.productListRecycler.adapter = adapter
                    }
                }
                else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<BranchOrderDTO>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}