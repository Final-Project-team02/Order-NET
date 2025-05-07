// 주문상세

package bitc.fullstack.app.Warehouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailBinding by lazy {
        ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: OrderAdapter
    private val orderItems = mutableListOf<OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Window insets 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView 설정
        adapter = OrderAdapter(orderItems)
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter

        // 더미 데이터 불러오기
        loadOrderItems()
    }

    private fun loadOrderItems() {
        repeat(3) {
            orderItems.add(OrderItem("a1011", "볼트", "엔진", "1개", "20,000", "20,000" ))
        }
        adapter.notifyDataSetChanged()
    }
}
