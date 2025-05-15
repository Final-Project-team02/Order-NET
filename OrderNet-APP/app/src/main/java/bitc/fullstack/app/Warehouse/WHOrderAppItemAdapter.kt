package bitc.fullstack.app.Warehouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.dto.WHOrderAppItemDTO
import bitc.fullstack.app.databinding.ItemOrderDetailBinding
import bitc.fullstack.app.databinding.ItemOrderDetailWhBinding
import com.bumptech.glide.Glide
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale


class WHOrderAppItemAdapter(private var items: List<WHOrderAppItemDTO>) :
    RecyclerView.Adapter<WHOrderAppItemAdapter.ViewHolder>() {

    // 데이터 리스트 갱신
    fun updateItems(newItems: List<WHOrderAppItemDTO>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailWhBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemOrderDetailWhBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WHOrderAppItemDTO) {

            Glide.with(binding.root.context)
                .load(item.partImg) // 이미지 URL
                .placeholder(android.R.color.darker_gray)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.ivProductImage)

            // 상품명, 수량, 가격 표시

            binding.tvProductCode.text = item.partId
            binding.tvProductName.text = item.partName
            binding.tvProductCate.text = item.partCate
            binding.tvProductQuantity.text = "수량: ${item.orderItemQuantity}"

            // 가격은 BigDecimal로 되어 있으므로, 포맷팅해서 출력
            binding.tvProductPrice.text = "금액 ${NumberFormat.getNumberInstance(Locale.KOREA).format(item.orderItemPrice.multiply(BigDecimal(item.orderItemQuantity)).setScale(0, BigDecimal.ROUND_DOWN).toDouble())} 원"



        }
    }
}
