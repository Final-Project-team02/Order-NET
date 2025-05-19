package bitc.fullstack.app.Branch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityBranchMainBinding
import bitc.fullstack.app.dto.BranchCountDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import bitc.fullstack.app.R

class BranchMainActivity : BranchBaseActivity() {

    private lateinit var userRefId: String
    private lateinit var branchName: String

    private val binding: ActivityBranchMainBinding by lazy {
        ActivityBranchMainBinding.inflate(layoutInflater)
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

        // 공통 툴바 메뉴 설정
        val menuButton = findViewById<ImageButton>(R.id.menu)
        val homeButton = findViewById<ImageButton>(R.id.home)

        setupToolbar(menuButton, homeButton)

        selectbranch()
        
        // 주문하기 버튼
        binding.orderButton.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
            val intent = Intent(this, BranchOrderResiActivity::class.java)
            intent.putExtra("userRefId", branchId) // userRefId 전달
            startActivity(intent)
        }
        
        // 신청(1) 버튼
        binding.orderApplyBtn.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "신청")
            intent.putExtra("userRefId", branchId) // userRefId 전달
            startActivity(intent)
        }

        // 승인 버튼
        binding.orderAcceptBtn.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "결재")
            intent.putExtra("userRefId", branchId) // userRefId 전달
            startActivity(intent)

        }

        // 출고 버튼
        binding.orderDeliveryBtn.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "출고")
            intent.putExtra("userRefId", branchId) // userRefId 전달
            startActivity(intent)
        }

        // 반려 버튼
        binding.orderHoldBtn.setOnClickListener {
            val branchId = intent.getStringExtra("userRefId") ?: ""
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "반려")
            intent.putExtra("userRefId", branchId)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        branchName = prefs.getString("branchName", "") ?: ""
        binding.branchName.text = branchName
    }

    // 대리점 로그인 첫 페이지
    private fun selectbranch() {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "") ?: ""
        val branchId = intent.getStringExtra("userRefId") ?: ""
        Log.d("BranchMain", "전달받은 branchId: $branchId")

        branchName = intent.getStringExtra("branchName") ?: ""
        binding.branchName.text = branchName

        // 저장
        prefs.edit().putString("branchName", branchName).apply()

        val api = AppServerClass.instance
        val call = api.BranchInfo("Bearer $token", branchId)
        retrofitResponse(call)
    }



//    private fun selectbranch(){
//        val api = AppServerClass.instance
//        val call = api.BranchInfo("Busan01")
//        retrofitResponse(call)
//    }

    private fun retrofitResponse(call: Call<BranchCountDTO>) {
        call.enqueue(object : Callback<BranchCountDTO> {
            override fun onResponse(p0: Call<BranchCountDTO>, res: Response<BranchCountDTO>) {
                if (res.isSuccessful) {

                    val data = res.body()
                    Log.d("csy", "branch 조회 결과 : $data")

                    data?.let {
//                        binding.branchName.text = it.branchName
                        binding.branchOrderCount.text = "${it.orderCount}건"
                        binding.branchAcceptCount.text = "${it.acceptCount}건"
                        binding.branchDeliveryCount.text = "${it.deliveryCount}건"
                        binding.branchHoldCount.text = "${it.holdCount}건"
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

            override fun onFailure(p0: Call<BranchCountDTO>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}