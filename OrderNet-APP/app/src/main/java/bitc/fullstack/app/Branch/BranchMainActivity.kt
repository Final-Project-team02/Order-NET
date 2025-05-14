package bitc.fullstack.app.Branch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityBranchMainBinding
import bitc.fullstack.app.dto.BranchCountDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BranchMainActivity : AppCompatActivity() {

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
        selectbranch()

//        selectbranch()
        
        // 주문하기 버튼
        binding.orderButton.setOnClickListener {
            val intent = Intent(this, BranchOrderResiActivity::class.java)
            startActivity(intent)
        }
        
        // 신청(1) 버튼
        binding.orderApplyBtn.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "신청")
            startActivity(intent)
        }

        // 승인 버튼
        binding.orderAcceptBtn.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "승인")
            startActivity(intent)
        }

        // 출고 버튼
        binding.orderDeliveryBtn.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "출고")
            startActivity(intent)
        }

        // 반려 버튼
        binding.orderHoldBtn.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            intent.putExtra("selectedStatus", "반려")
            startActivity(intent)
        }

    }

    // 대리점 로그인 첫 페이지
    private fun selectbranch() {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "") ?: ""
        val branchId = intent.getStringExtra("userRefId") ?: ""
        Log.d("BranchMain", "전달받은 branchId: $branchId")

        val branchName = intent.getStringExtra("branchName") ?: ""
        binding.branchName.text = branchName

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