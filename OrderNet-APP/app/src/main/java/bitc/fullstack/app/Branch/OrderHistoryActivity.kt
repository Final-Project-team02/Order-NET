package bitc.fullstack.app.Branch

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.Branch.BranchMainActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityOrderHistoryBinding
import bitc.fullstack.app.dto.BranchOrderDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class OrderHistoryActivity : AppCompatActivity() {

    private val binding: ActivityOrderHistoryBinding by lazy {
        ActivityOrderHistoryBinding.inflate(layoutInflater)
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var startDate: Calendar? = null
    private lateinit var orderList: List<BranchOrderDTO>

    private lateinit var userRefId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        홈 버튼
        val homeButton: ImageButton = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(this, BranchMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

//        메뉴 버튼
        val menuButton: ImageButton = findViewById(R.id.menu)

        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.branch_header_menu, popupMenu.menu)

            // 현재 액티비티가 OrderHistoryActivity이므로 "주문 현황" 메뉴 제거
            popupMenu.menu.removeItem(R.id.menu_stock)


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
                    R.id.btn_logout -> {
                        // 1. 저장된 값 삭제
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().clear().apply()

                        // 2. 로그인 화면으로 이동
                        val intent = Intent(this@OrderHistoryActivity, Login::class.java)
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

        userRefId = intent.getStringExtra("userRefId") ?: ""

        val spinner2 = binding.mySpinner2

        val items2 = listOf("신청", "결재", "출고", "반려")

        val adapter2 = object : ArrayAdapter<String>(
            this,
            R.layout.spinner_selected_item, // 선택된 항목에 사용할 레이아웃
            items2
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                // ✅ 선택된 항목 표시용 TextView만 반환해야 Spinner가 정상 출력함
                val textView = layoutInflater.inflate(R.layout.spinner_selected_item, parent, false)
                    .findViewById<TextView>(R.id.spinner_selected_text)
                textView.text = getItem(position)
                return textView
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = layoutInflater.inflate(R.layout.spinner_dropdown_item, parent, false)
                val textView = view.findViewById<TextView>(R.id.spinner_item_text)
                val divider = view.findViewById<View>(R.id.divider)

                textView.text = getItem(position)

                // ✅ 마지막 항목은 divider 숨기기
                if (position == count - 1) {
                    divider.visibility = View.GONE
                } else {
                    divider.visibility = View.VISIBLE
                }

                return view
            }
        }

        spinner2.adapter = adapter2

        // 선택된 상태를 Intent에서 받아서 Spinner에 반영
        val selectedStatus = intent.getStringExtra("selectedStatus") ?: "신청"
        val selectedIndex = items2.indexOf(selectedStatus)
        if (selectedIndex != -1) {
            spinner2.setSelection(selectedIndex)
        }


        selectBranchOrderList()

        // 시작 날짜 이벤트
        binding.btnStartDate.setOnClickListener {
            showDatePicker(binding.btnStartDate, true) // startDate 선택
        }
        // 종료 날짜 이벤트
        binding.btnEndDate.setOnClickListener {
            if (startDate == null) {
                showToast("시작 기간 날짜를 먼저 선택하세요.")
            } else {
                showDatePicker(binding.btnEndDate, false) // 종료 날짜 선택
            }
        }

        // 조회 버튼
        binding.btnOrderSearch.setOnClickListener {
            selectBranchOrderList()
        }


    }

    // 날짜 다이얼로그 설정
    private fun showDatePicker(targetButton: Button, isStartDate: Boolean) {
        val calendar = Calendar.getInstance()

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            if (isStartDate) {
                startDate = selectedDate
                targetButton.text = dateFormat.format(selectedDate.time)
            } else {
                if (startDate != null && selectedDate.timeInMillis < startDate!!.timeInMillis) {
                    showToast("종료 날짜는 시작 날짜 이후로 선택하세요.")
                } else {
                    targetButton.text = dateFormat.format(selectedDate.time)
                }
            }
        }

        val datePickerDialog = DatePickerDialog(
            this,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        if (!isStartDate) {
            if (startDate != null) {
                datePickerDialog.datePicker.minDate = startDate!!.timeInMillis
            } else {
                datePickerDialog.datePicker.minDate = calendar.timeInMillis
            }
        }

        datePickerDialog.show()
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // 주문 상태에 따른 출력
    private fun selectBranchOrderList() {
        val api = AppServerClass.instance
        val selectedText = binding.mySpinner2.selectedItem.toString()  // Spinner에서 선택된 값

        // 한글 매핑
        val statusMap = mapOf(
            "신청" to "승인 대기",
            "결재" to "결재",
            "출고" to "출고",
            "반려" to "반려"
        )

        val statusForQuery = statusMap[selectedText] ?: ""

        // 시작 날짜
        val startDateStr = binding.btnStartDate.text.toString().takeIf { it != "시작 기간 선택" }
        // 종료 날짜
        val endDateStr = binding.btnEndDate.text.toString().takeIf { it != "종료 기간 선택" }
        // 주문 번호
        val orderId = binding.editTextOrderNumber.text.toString().takeIf { it.isNotEmpty() }



        // 로그인 후에는 로그인한 id로
        val branchId = userRefId

        Log.d("csy", "branchId: $branchId, orderStatus: $statusForQuery, startDate: $startDateStr, endDate: $endDateStr, orderId: $orderId")

        // null 값일 경우 해당 파라미터를 제외하여 요청을 보냄
        val call = api.orderHistory(branchId, statusForQuery, startDateStr, endDateStr, orderId)
        retrofitResponse(call)
    }

    private fun retrofitResponse(call: Call<List<BranchOrderDTO>>) {
        call.enqueue(object : Callback<List<BranchOrderDTO>> {
            override fun onResponse(call: Call<List<BranchOrderDTO>>, response: Response<List<BranchOrderDTO>>) {
                if (response.isSuccessful) {
                    val orderList = response.body() ?: emptyList()
                    Log.d("csy", "받은 주문 목록: $orderList")

                    // 어댑터 연결 등 UI 업데이트 작업
                    val adapter = OrderListAdapter(orderList, this@OrderHistoryActivity, userRefId)
                    binding.recyclerOrderList.layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
                    binding.recyclerOrderList.adapter = adapter

                } else {
                    Log.d("csy", "송신 실패, 상태 코드: ${response.code()}, 메시지: ${response.message()}")
                    response.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(call: Call<List<BranchOrderDTO>>, t: Throwable) {
                Log.d("csy", "통신 오류: ${t.message}")
            }
        })
    }


}
