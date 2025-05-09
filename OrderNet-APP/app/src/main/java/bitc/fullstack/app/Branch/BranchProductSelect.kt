package bitc.fullstack.app.Branch


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.R
import bitc.fullstack.app.databinding.ActivityProjectSelectBinding

// 상품 추가
class BranchProductSelect : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSelectBinding
//    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 샘플(더미) 데이터 추가
        productList.add(
            Product("a1231", "볼트", "규격 120c", 20000, R.drawable.bolt, 1, false)
        )
        productList.add(
            Product("b1011", "배터리", "규격 5kg", 40000, R.drawable.battery, 3, true)
        )
        productList.add(
            Product("c222", "볼트", "규격 120c", 20000, R.drawable.bolt, 1, true)
        )

//        adapter = ProductAdapter(productList)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
//        binding.recyclerProducts.adapter = adapter
    }
}

