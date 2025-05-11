package bitc.fullstack.app.Branch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemOrderDetailBinding
import bitc.fullstack.app.dto.PartsDTO

class OrderItemAdapter(private val items: List<PartsDTO>) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.itemNumber.text = (position + 1).toString()
        // 부품명, 카테고리, 부품 코드, 가격, 선택 수량, 상태 등 표시
        holder.binding.partName.text = item.partName
        holder.binding.partCate.text = item.partCate
        holder.binding.partId.text = item.partId
        holder.binding.partPrice.text = String.format("%,d원", item.orderItemPrice)
        holder.binding.partCount.text = "${item.orderItemQuantity}개"

        // 총 금액 계산
        val totalPrice = item.orderItemPrice * item.orderItemQuantity
        holder.binding.totalPrice.text = String.format("%,d원", totalPrice)

        // 상세 상태
        holder.binding.partStatus.text = item.orderItemStatus
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ItemOrderDetailBinding) : RecyclerView.ViewHolder(binding.root)
}
