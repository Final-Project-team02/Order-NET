package bitc.fullstack.app.Branch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemOrderListBinding
import bitc.fullstack.app.databinding.ItemProductBinding

// 상품추가 어뎁터

class ProductAdapter(private val items: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemOrderListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.checkboxSelect.isChecked = item.isChecked
            binding.imgProduct.setImageResource(item.imageResId)
            binding.txtCode.text = item.code
            binding.txtName.text = item.name
            binding.txtSpec.text = item.spec
            binding.txtCount.text = item.count.toString()
            binding.txtPrice.text = String.format("%,d원", item.price)

            // 수량 증가
            binding.btnPlus.setOnClickListener {
                item.count++
                binding.txtCount.text = item.count.toString()
            }

//            수량 감소
            binding.btnMinus.setOnClickListener {
                if (item.count > 1) {
                    item.count--
                    binding.txtCount.text = item.count.toString()
                }
            }

            // 체크박스 상태 갱신
            binding.checkboxSelect.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            // 담기 버튼 클릭 시
            binding.btnAdd.setOnClickListener {
                Toast.makeText(binding.root.context, "${item.name} 담김", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemOrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}


