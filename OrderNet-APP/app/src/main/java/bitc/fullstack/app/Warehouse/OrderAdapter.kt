package bitc.fullstack.app.Warehouse


// 주문 상세 어뎁터

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemOrderRowBinding

class OrderAdapter(private val items: List<OrderItem>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]
        holder.binding.code.text = item.code
        holder.binding.part1.text = item.part1
        holder.binding.part2.text = item.part2
        holder.binding.quantity.text = item.quantity
        holder.binding.price1.text = item.price1
        holder.binding.price2.text = item.price2
    }

    override fun getItemCount(): Int = items.size
}
