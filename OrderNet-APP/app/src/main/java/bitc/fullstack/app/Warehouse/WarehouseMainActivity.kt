package bitc.fullstack.app.Warehouse

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.R
import bitc.fullstack.app.databinding.ActivityWarehouseMainBinding
import java.util.*

class WarehouseMainActivity : AppCompatActivity() {

    private var isDropdownVisible = false  // 드롭다운 상태 관리
    private lateinit var spinnerOrderList: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityWarehouseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 스피너 세팅
        spinnerOrderList = findViewById(R.id.spinner_order_list)


        setupSpinner()

        // 시작 날짜 버튼 클릭 리스너
        binding.btnStartDate.setOnClickListener {
            showDatePickerDialog(binding.btnStartDate)
        }

        // 종료 날짜 버튼 클릭 리스너
        binding.btnEndDate.setOnClickListener {
            showDatePickerDialog(binding.btnEndDate)
        }

    }

    // 스피너 항목 설정
    private fun setupSpinner() {
        val orderOptions = listOf("전체", "출고 대기", "출고 완료")  // 스피너에 표시할 목록
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, orderOptions)
        spinnerOrderList.adapter = adapter
    }

    // 날짜 선택 다이얼로그
    private fun showDatePickerDialog(targetButton: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                targetButton.text = selectedDate
            },
            year, month, day
        )

        datePickerDialog.show()
    }
}
