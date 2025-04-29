package bitc.fullstack.app.Client

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.R
import bitc.fullstack.app.databinding.ActivityOrderHistoryBinding

class BranchOrderHistory : AppCompatActivity() {
    private val binding: ActivityOrderHistoryBinding by lazy {
        ActivityOrderHistoryBinding.inflate(layoutInflater)
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


        val spinner1: Spinner = findViewById(R.id.my_spinner1)
        val spinner2: Spinner = findViewById(R.id.my_spinner2)

        val items1 = listOf("주문 일자", "옵션 1", "옵션 2", "옵션 3")
        val items2 = listOf("신청", "옵션 1", "옵션 2", "옵션 3")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items1)
        spinner1.adapter = adapter1

        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items2)
        spinner2.adapter = adapter2

    }
}