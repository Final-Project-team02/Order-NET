package bitc.fullstack.app.Warehouse

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.Branch.BranchMainActivity
import bitc.fullstack.app.Branch.BranchOrderResiActivity
import bitc.fullstack.app.Branch.OrderHistoryActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.appserver.AppServerInterface
import bitc.fullstack.app.databinding.ActivityWarehouseMainBinding
import bitc.fullstack.app.dto.OrderAppDTO
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WHMainActivity : AppCompatActivity() {

    private lateinit var whOrderAdapter: WHOrderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerOrderList: Spinner
    private lateinit var apiService: AppServerInterface
    private lateinit var binding: ActivityWarehouseMainBinding
    private lateinit var userRefId: String
    private lateinit var warehouseName: String

    private var fullOrders: List<OrderAppDTO> = emptyList()
    private var selectedStatus: String = "전체"
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarehouseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRefId = intent.getStringExtra("userRefId") ?: ""

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
                        val intent = Intent(this@WHMainActivity, Login::class.java)
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


        // RecyclerView 설정
        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager = LinearLayoutManager(this)
        whOrderAdapter = WHOrderAdapter(emptyList(), userRefId)
        recyclerView.adapter = whOrderAdapter

        // 스피너 설정
        spinnerOrderList = binding.spinnerOrderList
        setupSpinner()

        spinnerOrderList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, pos: Int, id: Long
            ) {
                selectedStatus = parent.getItemAtPosition(pos).toString()
                filterOrders()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // 날짜 버튼 클릭
        binding.btnStartDate.setOnClickListener {
            showDatePickerDialog(binding.btnStartDate) { date ->
                startDate = date
                filterOrders()
            }
        }

        binding.btnEndDate.setOnClickListener {
            showDatePickerDialog(binding.btnEndDate) { date ->
                endDate = date
                filterOrders()
            }
        }

        binding.etOrderNumber.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = binding.etOrderNumber.compoundDrawables[2]  // 아이콘을 가져옵니다.

                // drawableEnd가 null이 아니면 아이콘 클릭을 감지할 수 있습니다.
                if (drawableEnd != null) {
                    val iconLeft = binding.etOrderNumber.width - drawableEnd.bounds.width()  // 아이콘의 왼쪽 위치
                    val iconRight = binding.etOrderNumber.width  // 아이콘의 오른쪽 위치

                    // 클릭된 좌표가 아이콘 영역 안에 있을 경우
                    if (event.x >= iconLeft && event.x <= iconRight) {
                        // 아이콘 클릭 시 필터링 적용
                        filterOrders()
                        return@setOnTouchListener true
                    }
                }
            }
            false  // 다른 터치 이벤트는 기본적으로 처리
        }


        userRefId = intent.getStringExtra("userRefId") ?: ""
        warehouseName = intent.getStringExtra("warehouseName") ?: ""

        // 데이터 불러오기
//        getOrdersByWarehouse("WH_BRK")
        getOrdersByWarehouse(userRefId)
    }



//    private fun setupSpinner() {
//        val options = listOf("전체", "출고대기", "출고완료")
//        spinnerOrderList.adapter = ArrayAdapter(
//            this, android.R.layout.simple_spinner_dropdown_item, options
//        )
//    }

    // ✅ Spinner에 커스텀 레이아웃 적용
    private fun setupSpinner() {
        val options = listOf("전체", "출고대기", "출고완료")

        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.warehouse_main_select_spinner_selected_item, // 선택된 항목 레이아웃
            options
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.warehouse_main_select_spinner_selected_item, parent, false)
                val textView = view.findViewById<TextView>(R.id.spinner_selected_text)
                textView.text = getItem(position)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.warehouse_main_select_spinner_dropdown_item, parent, false)
                val textView = view.findViewById<TextView>(R.id.spinner_item_text)
                textView.text = getItem(position)
                return view
            }
        }

        spinnerOrderList.adapter = adapter
    }

    private fun showDatePickerDialog(target: Button, onDateSet: (String) -> Unit) {
        val cal = Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            val date = String.format("%04d-%02d-%02d", y, m + 1, d)
            target.text = date
            onDateSet(date)
        }

        DatePickerDialog(
            this,
            listener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getOrdersByWarehouse(warehouseId: String) {
        val api = AppServerClass.instance
        val call = api.getOrdersByWarehouse(warehouseId)
        retrofitResponse(call) { result ->
            fullOrders = result ?: emptyList()
            whOrderAdapter.updateData(fullOrders)
        }
    }

    private fun filterOrders() {
        val status = spinnerOrderList.selectedItem.toString()
        var filtered = if (status == "전체") {
            fullOrders
        } else {
            fullOrders.filter { it.orderItemStatus == status }
        }

        // 날짜 필터링 처리
        if (!startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val start = sdf.parse(startDate)
            val end = sdf.parse(endDate)

            if (start != null && end != null) {
                filtered = filtered.filter {
                    it.orderDate != null &&
                            it.orderDate >= start && it.orderDate <= end
                }
            }
        }

        val orderNumber = binding.etOrderNumber.text.toString()
        if (orderNumber.isNotEmpty()) {
            filtered = filtered.filter { it.orderId.contains(orderNumber, ignoreCase = true) }
        }

        whOrderAdapter.updateData(filtered)
    }

    private fun <T> retrofitResponse(call: Call<T>, onSuccess: (T?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    onSuccess(body)
                    Log.d("WHMainActivity", "서버 응답 성공, 결과: ${Gson().toJson(body)}")
                } else {
                    val error = response.errorBody()?.string()
                    Toast.makeText(this@WHMainActivity, "서버 오류: $error", Toast.LENGTH_SHORT).show()
                    android.util.Log.e("WHMainActivity", "서버 응답 실패: ${response.code()}, ${response.message()}")
                    android.util.Log.e("WHMainActivity", "서버 오류 내용: $error")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Toast.makeText(this@WHMainActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
