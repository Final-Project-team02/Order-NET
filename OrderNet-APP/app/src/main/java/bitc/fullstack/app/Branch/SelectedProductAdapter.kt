package bitc.fullstack.app.Branch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemProductBinding
import bitc.fullstack.app.dto.PartsDTO

class SelectedProductAdapter(    private val items: List<PartsDTO>,
                                 private val activity: BranchOrderResiActivity) :
    RecyclerView.Adapter<SelectedProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PartsDTO, position: Int) {
            binding.itemNumber.text = (position + 1).toString()
            binding.partName.text = item.partName
            binding.partCate.text = item.partCate
            binding.partId.text = item.partId
            binding.partPrice.text = String.format("%,d원", item.partPrice)
            binding.partCount.text = "${item.count}개"
            val itemTotalPrice = item.partPrice * item.count
            binding.totalPrice.text = String.format("%,d원", itemTotalPrice)
        }
    }

    fun updateTotalAmount() {
        // 각 항목의 총합
        var total = 0L
        for (item in items) {
            val itemTotal = item.partPrice * item.count
            total += itemTotal
        }
        Log.d("SelectedProductAdapter", "Calculated Total Amount: $total")
        activity.updateTotalValue(total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    // position - items 리스트 안 바인딩하려는 아이템이 몇 번째인지 나타냄
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size


}
