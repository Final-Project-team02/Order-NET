package bitc.fullstack.app.Warehouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.databinding.ActivityWhorderHistoryBinding
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData

class WHOrderHistory : AppCompatActivity() {

    private val binding: ActivityWhorderHistoryBinding by lazy {
        ActivityWhorderHistoryBinding.inflate(layoutInflater)
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

        setupPieChart()
    }

    private fun setupPieChart() {
        val entries = listOf(
            PieEntry(5000f, "주문"),
            PieEntry(5000f, "줄구")
        )

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(0xFF36A2EB.toInt(), 0xFFFF6384.toInt()) // 파랑, 빨강
            valueTextSize = 16f
        }

        val data = PieData(dataSet)

        binding.pieChart.apply {
            this.data = data
            setUsePercentValues(true)
            isDrawHoleEnabled = true
            description.isEnabled = false
            invalidate() // 차트 갱신
        }
    }
}
