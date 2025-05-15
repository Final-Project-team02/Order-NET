package bitc.fullstack.app.Branch

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.Branch.BranchMainActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityBranchOrderRegiBinding
import bitc.fullstack.app.databinding.OrderPopupBinding
import bitc.fullstack.app.dto.BranchDTO
import bitc.fullstack.app.dto.OrderItemDTO
import bitc.fullstack.app.dto.OrderRequestDTO
import bitc.fullstack.app.dto.PartsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BranchOrderResiActivity : AppCompatActivity() {

    private val binding: ActivityBranchOrderRegiBinding by lazy {
        ActivityBranchOrderRegiBinding.inflate(layoutInflater)
    }

    lateinit var selectedAdapter: SelectedProductAdapter
    private val selectedItems: MutableList<PartsDTO> = mutableListOf()

    private lateinit var userRefId: String
    private lateinit var branchName: String

    internal  lateinit var selectProductLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        userRefId = intent.getStringExtra("userRefId") ?: ""
        branchName = intent.getStringExtra("userRefId") ?: ""

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

            // 현재 액티비티가 BranchOrderResiActivity이므로 "주문하기" 메뉴 제거
            popupMenu.menu.removeItem(R.id.menu_order)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {

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
                        val intent = Intent(this@BranchOrderResiActivity, Login::class.java)
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

        firstConnection(userRefId)

        // 결과 받아오기 위한 selectProductLauncher 등록
        selectProductLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val selectedProduct = data?.getSerializableExtra("selectedProduct") as? PartsDTO
                val selectedItemsList = data?.getSerializableExtra("selectedItems") as? ArrayList<PartsDTO> // 여러 상품 선택 시

                if (selectedProduct != null) {
                    // 선택된 상품 하나만 갱신
                    val position = data.getIntExtra("position", -1)
                    if (position != -1) {
                        selectedAdapter.updateItem(position, selectedProduct)
                        selectedAdapter.updateTotalAmount()
                    }
                }

                if (selectedItemsList != null) {
                    // 여러 상품이 선택된 경우
//                    selectedItems.clear()  // 기존 아이템들 초기화
                    selectedItems.addAll(selectedItemsList)  // 새로운 아이템들 추가
                    selectedAdapter.notifyDataSetChanged()  // RecyclerView 갱신

                    selectedAdapter.updateTotalAmount()

                    // 로그로 확인
                    Log.d("BranchOrderResiActivity", "selectedItems (multiple): $selectedItems")
                }
            }
        }

        // 어댑터 연결
        selectedAdapter = SelectedProductAdapter(selectedItems, this)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = selectedAdapter

        // Adapter에서 데이터를 다 바인딩한 후 총합 업데이트, 총합을 Activity에 전달
        selectedAdapter.updateTotalAmount()

        // 주문일자 클릭 비활성화
            binding.orderDate.isEnabled = false

        // 납품일자 클릭 시 달력 띄우기
        binding.orderDueDate.setOnClickListener {
            val orderDateText = binding.orderDate.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val orderDate: Date? = sdf.parse(orderDateText)

            showDatePicker(minDate = orderDate?.time) { selectedDate ->
                binding.orderDueDate.text = selectedDate
            }
        }

        // 상품추가 버튼
        binding.productPlusBtn.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
            val intent = Intent(this, ProjectSelectActivity::class.java)
            intent.putExtra("userRefId", branchId) // userRefId 전달
            selectProductLauncher.launch(intent)
        }


        // 주문신청 버튼
        binding.productApplyBtn.setOnClickListener {
            val dialogBinding = OrderPopupBinding.inflate(layoutInflater)
            val alertDialog = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .create()

            // 팝업 수락 버튼
            dialogBinding.orderSubmitBtn.setOnClickListener {
                // 화면에서 필요한 값들 꺼내기
                val orderDate = binding.orderDate.text.toString()
                val dueDate = binding.orderDueDate.text.toString()
                //val totalAmount = selectedItems.sumOf { it.partPrice * it.count }
                val totalAmount = binding.totalValue.text.toString().replace(",", "").replace("원", "").toInt()


                // 주문 항목 리스트 만들기
                val orderItems = selectedItems.map {
                    OrderItemDTO(
                        partId = it.partId,
                        orderItemQuantity = it.count,
                        orderItemPrice = it.partPrice
                    )
                }

                // OrderRequest 객체 생성
                val orderRequest = OrderRequestDTO(
                    orderDate = orderDate,
                    orderDueDate = dueDate,
                    orderPrice = totalAmount,
                    // 로그인하고 나면 해당 대리점 id로 변경
                    branchId = userRefId,
                    items = orderItems
                )
                // 주문 요청 서버로 전송
                placeOrder(orderRequest)

                // 팝업 닫기
                alertDialog.dismiss()
            }

            // 팝업 취소 버튼
            dialogBinding.orderCancelBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }

    // 주문하기 화면 접속 후 출력 화면
    private fun firstConnection(userRefId: String){
        val api = AppServerClass.instance
        // 나중에 로그인하면 로그인한 사용자 user-ref-id 전달
        val call = api.BranchOrder(userRefId)
        retrofitResponse(call)
    }

    // 주문, 납품 일자 선택
    private fun showDatePicker(minDate: Long? = null, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // month는 0부터 시작하기에 +1
            val selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(selectedDate)
        }, year, month, day)

        // 최소 선택 날짜 설정
        minDate?.let {
            datePickerDialog.datePicker.minDate = it
        }

        datePickerDialog.show()
    }


    private fun retrofitResponse(call: Call<List<BranchDTO>>) {
        call.enqueue(object : Callback<List<BranchDTO>> {
            override fun onResponse(p0: Call<List<BranchDTO>>, res: Response<List<BranchDTO>>) {
                if (res.isSuccessful) {

                    val branchList = res.body()
                    if (branchList != null && branchList.isNotEmpty()) {
                        // 첫 번째 항목을 가져와서 UI 업데이트
                        val branch = branchList[0]
                        Log.d("csy", "Branch info: $branch")

                        // DTO 값 바인딩
                        binding.orderNameValue.text = branch.branchName
                        binding.deliveryPlace.text = branch.branchName
                        binding.phoneNumValue.text = branch.branchPhone
                        binding.deliveryPlaceNum.text = branch.branchZipCode
                        binding.deliveryPlaceAddr.text = branch.branchRoadAddr

                        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        binding.orderDate.text = today
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

            override fun onFailure(p0: Call<List<BranchDTO>>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }

    // totalAmount 값 전달받아 출력
    fun updateTotalValue(totalAmount: Long) {
        binding.totalValue.text = String.format("%,d원", totalAmount)
    }


    // 주문 데이터를 서버로 전송
    private fun placeOrder(orderRequest: OrderRequestDTO) {
        val api = AppServerClass.instance
        val call = api.placeOrder(orderRequest)  // 주문을 서버로 전송하는 API 호출
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
                    val branchName = intent.getStringExtra("branchName") ?: "" //  userRefId 재사용
                    // 주문 성공
                    Toast.makeText(this@BranchOrderResiActivity, "주문이 신청되었습니다.", Toast.LENGTH_SHORT).show()
                    // 메인화면으로 이동
                    val intent = Intent(this@BranchOrderResiActivity, BranchMainActivity::class.java)
                    intent.putExtra("userRefId", branchId)
                    intent.putExtra("branchName", branchName)
                    startActivity(intent)
                } else {
                    // 주문 실패
                    Toast.makeText(this@BranchOrderResiActivity, "주문에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 등으로 실패
                Toast.makeText(this@BranchOrderResiActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // selectedAdapter에 있는 아이템 갱신 메소드
    private fun updateItem(position: Int, newItem: PartsDTO) {
        selectedItems[position] = newItem
        selectedAdapter.notifyItemChanged(position)
    }
}