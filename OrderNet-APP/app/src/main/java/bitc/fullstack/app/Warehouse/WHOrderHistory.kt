package bitc.fullstack.app.Warehouse

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.appserver.AppServerInterface
import bitc.fullstack.app.databinding.ActivityWhorderHistoryBinding
import bitc.fullstack.app.dto.WHDTO
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WHOrderHistory : AppCompatActivity() {

    private val binding: ActivityWhorderHistoryBinding by lazy {
        ActivityWhorderHistoryBinding.inflate(layoutInflater)
    }
    private lateinit var apiService: AppServerInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Retrofit 초기화
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.100.203.54:8080/") // 서버 주소
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(AppServerInterface::class.java)

        setupSpinner()
        // setupPieChart()
    }

    private fun setupSpinner() {
        val options = listOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")

        binding.spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            options
        )

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, pos: Int, id: Long
            ) {
                val selectedMonth = parent.getItemAtPosition(pos).toString()
                filterByMonth(selectedMonth)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.spinner.setSelection(4)    }

    private fun filterByMonth(monthStr: String) {
        val month = monthStr.replace("월", "").toInt()
        val year = 2025 // 예시 연도
        val warehouseId = "WH_BRK" // 실제 물류센터 ID로 대체

        Log.d("WH_API", "요청 파라미터: warehouseId=$warehouseId, month=$month, year=$year")

        val api = AppServerClass.instance
        val call = api.getMonthlyOutboundParts(warehouseId, month, year)

        call.enqueue(object : Callback<List<WHDTO>> {
            override fun onResponse(call: Call<List<WHDTO>>, response: Response<List<WHDTO>>) {
                if (response.isSuccessful) {
                    val dataList = response.body() ?: emptyList()

                    Log.d("WH_API", "받은 데이터 수: ${dataList.size}")
                    Log.d("WH_API", "받은 데이터 내용: ${Gson().toJson(dataList)}")

                    if (dataList.isEmpty()) {
                        Log.d("WH_API", "데이터가 없습니다.")
                    }

                    val entries = dataList.map {
                        PieEntry(it.totalQuantity.toFloat(), it.partId)
                    }

                    showPieChart(entries)

                    binding.recyclerView.layoutManager = LinearLayoutManager(this@WHOrderHistory)
                    binding.recyclerView.adapter = WHOrderHistoryAdapter(dataList)
                } else {
                    Log.e("WH_API", "응답 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<WHDTO>>, t: Throwable) {
                Log.e("WH_API", "서버 호출 실패: ${t.message}")
                t.printStackTrace()
            }
        })
    }


//    private fun filterByMonth(monthStr: String) {
//        val month = monthStr.replace("월", "").toInt()
//        val year = 2025 // 예시 연도, 실제 연도로 대체 가능
//        val warehouseId = "WH_BRK" // 물류센터 ID를 실제 값으로 대체
//
//        Log.d("WH_API", "요청 파라미터: warehouseId=$warehouseId, month=$month, year=$year")
//
//        val api = AppServerClass.instance
//        val call = api.getMonthlyOutboundParts(warehouseId, month, year)
//
//        // Retrofit 호출
//        apiService.getMonthlyOutboundParts(warehouseId, month, year)
//            .enqueue(object : Callback<List<WHDTO>> { // 수정된 부분
//                override fun onResponse(
//                    call: Call<List<WHDTO>>,
//                    response: Response<List<WHDTO>>
//                ) {
//                    if (response.isSuccessful) {
//                        val dataList = response.body() ?: emptyList()
//
//                        Log.d("WH_API", "받은 데이터 수: $dataList") // 로그 확인
//
//                        Log.d("WH_API", "받은 데이터 내용: ${Gson().toJson(dataList)}")
//
//                        if (dataList.isEmpty()) {
//                            Log.d("WH_API", "데이터가 없습니다.")
//                        }
//                        val entries = dataList.map {
//                            PieEntry(it.totalQuantity.toFloat(), it.partId)
//
//                        }
//                        Log.d("WH_API", "데이터가 nocome.")
//
//                        showPieChart(entries)
//
//                        // 🔹 리사이클러뷰 어댑터 연결
//                        binding.recyclerView.layoutManager = LinearLayoutManager(this@WHOrderHistory)
//                        // 🔹 RecyclerView 어댑터 연결
//                        binding.recyclerView.adapter = WHOrderHistoryAdapter(dataList)
//                    }
//                    else {
//                        Log.e("WH_API", "응답 실패: ${response.code()}")
//                    }
//                }
//                override fun onFailure(call: Call<List<WHDTO>>, t: Throwable) {
//                    Log.e("WH_API", "서버 호출 실패: ${t.message}")
//                    t.printStackTrace()
//                }
//            })
//    }

    private fun showPieChart(entries: List<PieEntry>) {
        if (entries.isEmpty()) {
            binding.pieChart.clear()
            binding.pieChart.setNoDataText("데이터가 없습니다.")
            binding.pieChart.invalidate()
            return
        }
        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                0xFFEF5350.toInt(), // 빨강
                0xFF42A5F5.toInt(), // 파랑
                0xFF66BB6A.toInt(), // 초록
                0xFFFFA726.toInt(), // 주황
                0xFFAB47BC.toInt(), // 보라
            )
            valueTextSize = 16f
        }

        val data = PieData(dataSet)
        binding.pieChart.apply {
            this.data = data
            notifyDataSetChanged() // 데이터 변경 알림
            setUsePercentValues(false)
            isDrawHoleEnabled = true
            description.isEnabled = false
            invalidate()
            visibility = View.VISIBLE // 혹시 안 보이는 상태일 경우 강제로 보이게
        }
    }


}
