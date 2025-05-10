package bitc.fullstack.app.Branch


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.R
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityProjectSelectBinding
import bitc.fullstack.app.dto.BranchDTO
import bitc.fullstack.app.dto.PartsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 상품 추가
class ProjectSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSelectBinding
    private lateinit var adapter: ProductAdapter
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

        selectPartsInfo()

        // 장바구니 버튼
        binding.btnCart.setOnClickListener {
            val selectedItems = productList.filter { it.isChecked }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "선택된 항목이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, BranchOrderResiActivity::class.java)
            intent.putExtra("selectedItems", ArrayList(selectedItems))
            startActivity(intent)
        }


        // 닫기버튼
        binding.btnClose.setOnClickListener {
            val intent = Intent(this, BranchOrderResiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectPartsInfo(){
        val api = AppServerClass.instance
        val call = api.ProductChoose()
        retrofitResponse(call)
    }

    private fun retrofitResponse(call: Call<List<PartsDTO>>) {
        call.enqueue(object : Callback<List<PartsDTO>> {
            override fun onResponse(p0: Call<List<PartsDTO>>, res: Response<List<PartsDTO>>) {
                if (res.isSuccessful) {

                    val partsList = res.body()
                    if (!partsList.isNullOrEmpty()) {
                        Log.d("csy", "받은 부품 목록: $partsList")
                        // 기존 productList 제거
                        productList.clear()
                        // 서버에서 받아온 데이터 productList 추가
                        productList.addAll(partsList)
                        // 어댑터가 데이터 변경 확인 후 UI 새로 그림
                        adapter.notifyDataSetChanged()
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

            override fun onFailure(p0: Call<List<PartsDTO>>, t: Throwable) {
                Log.d("csy", "message : $t.message")
            }
        })
    }
}

