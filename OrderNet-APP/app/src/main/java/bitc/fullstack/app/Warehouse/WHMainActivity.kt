package bitc.fullstack.app.Warehouse

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.appserver.AppServerInterface
import bitc.fullstack.app.databinding.ActivityWarehouseMainBinding
import bitc.fullstack.app.dto.OrderAppDTO
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class WHMainActivity : AppCompatActivity() {

    private lateinit var whOrderAdapter: WHOrderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerOrderList: Spinner
    private lateinit var apiService: AppServerInterface
    private var fullOrders: List<OrderAppDTO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWarehouseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.100.203.54:8080/app/") // 서버 주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(AppServerInterface::class.java)

        // RecyclerView 설정
        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager = LinearLayoutManager(this)
        whOrderAdapter = WHOrderAdapter(emptyList())
        recyclerView.adapter = whOrderAdapter

        // 스피너 설정
        spinnerOrderList = binding.spinnerOrderList
        setupSpinner()

        spinnerOrderList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, pos: Int, id: Long
            ) {
                val status = parent.getItemAtPosition(pos).toString()
                filterByStatus(status)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnStartDate.setOnClickListener {
            showDatePickerDialog(binding.btnStartDate)
        }
        binding.btnEndDate.setOnClickListener {
            showDatePickerDialog(binding.btnEndDate)
        }

        // 데이터 불러오기
        getOrdersByWarehouse("WH_BRK")
    }

    private fun setupSpinner() {
        val options = listOf("전체", "출고 대기", "출고 완료")
        spinnerOrderList.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, options
        )
    }

    private fun showDatePickerDialog(target: Button) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, y, m, d -> target.text = String.format("%04d-%02d-%02d", y, m + 1, d) },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    private fun getOrdersByWarehouse(warehouseId: String) {
        retrofitResponse(apiService.getOrdersByWarehouse(warehouseId)) { result ->
            fullOrders = result ?: emptyList()
            whOrderAdapter.updateData(fullOrders)
        }
    }

    private fun filterByStatus(status: String) {
        val filtered = if (status == "전체") {
            fullOrders
        } else {
            fullOrders.filter { it.orderItemStatus == status }
        }
        whOrderAdapter.updateData(filtered)
    }


    private fun <T> retrofitResponse(call: Call<T>, onSuccess: (T?) -> Unit) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
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
