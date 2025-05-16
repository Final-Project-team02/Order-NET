package bitc.fullstack.app.Branch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.Branch.BranchMainActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityProjectSelectBinding
import bitc.fullstack.app.dto.PartsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 상품 추가
class ProjectSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSelectBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var fullList: List<PartsDTO>
    private val productList = mutableListOf<PartsDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // RecyclerView에 데이터 표시하기 위한 어댑터 클래스
        adapter = ProductAdapter(productList)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        // 어댑터 RecyclerView 연결
        binding.recyclerProducts.adapter = adapter

        binding.spinnerCategory.setDropDownHeightCompat(resources.getDimensionPixelSize(R.dimen.spinner_max_height))


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
                        val intent = Intent(this@ProjectSelectActivity, Login::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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

        selectPartsInfo()


        // 상품 변경 - 기본값 : FALSE, 상품변경 버튼으로 들어오면 TRUE
        val isEditMode = intent.getBooleanExtra("editMode", false)
        Log.d("ProjectSelectActivity", "Edit Mode: $isEditMode")
        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? PartsDTO

        // 장바구니 버튼
        binding.btnCart.setOnClickListener {
            val selectedItems = productList.filter { it.isChecked }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "선택된 항목이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isEditMode) {
                if (selectedItems.size != 1) {
                    Toast.makeText(this, "하나의 상품만 선택하세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // 하나의 상품만 선택된 상태에서, 그 상품을 반환
                selectedItems[0]?.let {
                    val resultIntent = Intent().apply {
                        putExtra("selectedProduct", it) // 선택된 상품을 반환
                        putExtra("position", intent.getIntExtra("position", -1)) // 위치 정보 전달
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                // EditMode가 false일 경우: 여러 개의 상품을 선택할 수 있음
                val resultIntent = Intent().apply {
                    putExtra("selectedItems", ArrayList(selectedItems)) // 선택된 여러 상품을 반환
                    Log.d("ProjectSelectActivity", "selectedItems: $selectedItems")
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // 닫기버튼
        binding.btnClose.setOnClickListener {
            finish()
        }

        // 조회 버튼
        binding.btnSearch.setOnClickListener {
            val searchQuery = binding.editSearch.text.toString().trim()
            applySearchQuery(searchQuery) // 검색어로 필터링
        }

        // Spinner 카테고리 선택 리스너 설정
        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = parentView?.getItemAtPosition(position).toString()
                    applyFilters(selectedCategory) // 카테고리로 필터링
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // 아무 것도 선택되지 않았을 때 (보통 사용되지 않지만 기본 구현)
                }
            }




    }

    // 서버에서 부품 정보 가져오기
    private fun selectPartsInfo() {
        val api = AppServerClass.instance
        val call = api.ProductChoose(null)
        retrofitResponse(call)
    }

    // 카테고리 필터링을 위한 함수
    private fun applyFilters(category: String) {
        val filteredList = if (category == "전체") {
            fullList // "전체" 선택 시 모든 상품을 보여줍니다.
        } else {
            fullList.filter { it.partCate == category } // 선택된 카테고리에 맞는 상품만 필터링
        }

        // 필터링된 리스트로 RecyclerView 갱신
        productList.clear()
        productList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

    // 검색어를 기준으로 필터링하는 함수
    private fun applySearchQuery(query: String) {
        if (query.isBlank()) {
            // 검색어가 비어있다면, 필터링하지 않고 전체 리스트를 보여줍니다.
            productList.clear()
            productList.addAll(fullList)
            adapter.notifyDataSetChanged()
            return
        }

        val filteredList = fullList.filter {
            it.partId.contains(query, ignoreCase = true) || // part_id로 검색
                    it.partCate.contains(query, ignoreCase = true) || // part_cate로 검색
                    it.partName.contains(query, ignoreCase = true)    // part_name으로 검색
        }

        // 필터링된 리스트로 RecyclerView 갱신
        productList.clear()
        productList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }


    // 필터링된 상품 목록을 받아오는 함수
    private fun retrofitResponse(call: Call<List<PartsDTO>>) {
        call.enqueue(object : Callback<List<PartsDTO>> {
            override fun onResponse(p0: Call<List<PartsDTO>>, res: Response<List<PartsDTO>>) {
                if (res.isSuccessful) {
                    val partsList = res.body()
                    if (!partsList.isNullOrEmpty()) {
                        // 전체 상품 목록 저장
                        fullList = partsList
                        // 카테고리 목록 추출 (중복 제거 및 '전체' 카테고리 추가)
                        val categories = listOf("전체") + partsList.map { it.partCate }.distinct()

                        // ✅ 커스텀 SpinnerAdapter 적용
                        val spinnerAdapter = object : ArrayAdapter<String>(
                            this@ProjectSelectActivity,
                            R.layout.project_select_spinner_selected_item,
                            categories
                        ) {
                            override fun getView(
                                position: Int,
                                convertView: View?,
                                parent: ViewGroup
                            ): View {
                                val view = layoutInflater.inflate(
                                    R.layout.project_select_spinner_selected_item,
                                    parent,
                                    false
                                )
                                val textView =
                                    view.findViewById<TextView>(R.id.spinner_selected_text)
                                textView.text = getItem(position)
                                return view
                            }

                            override fun getDropDownView(
                                position: Int,
                                convertView: View?,
                                parent: ViewGroup
                            ): View {
                                val view = layoutInflater.inflate(
                                    R.layout.project_select_spinner_dropdown_item,
                                    parent,
                                    false
                                )
                                val textView = view.findViewById<TextView>(R.id.spinner_item_text)
                                textView.text = getItem(position)
                                return view
                            }
                        }

                        binding.spinnerCategory.adapter = spinnerAdapter

                        Log.d("csy", "받은 부품 목록: $partsList")
                        // 기존 productList 제거
                        productList.clear()
                        // 서버에서 받아온 데이터 productList 추가
                        productList.addAll(partsList)
                        // 어댑터가 데이터 변경 확인 후 UI 새로 그림
                        adapter.notifyDataSetChanged()
                    }

                } else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<List<PartsDTO>>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }

    fun AppCompatSpinner.setDropDownHeightCompat(heightPx: Int) {
        try {
            val popupField = AppCompatSpinner::class.java.getDeclaredField("mPopup")
            popupField.isAccessible = true
            val popupWindow = popupField.get(this)
            val listPopupWindowClass = Class.forName("androidx.appcompat.widget.ListPopupWindow")
            if (listPopupWindowClass.isInstance(popupWindow)) {
                val setHeightMethod = listPopupWindowClass.getDeclaredMethod("setHeight", Int::class.javaPrimitiveType)
                setHeightMethod.invoke(popupWindow, heightPx)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}

