package bitc.fullstack.app.Branch

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemOrderBinding
import bitc.fullstack.app.dto.BranchOrderDTO

class OrderListAdapter(
    private val items: List<BranchOrderDTO>, // 주문 데이터 리스트
    private val activity: OrderHistoryActivity // 액티비티 참조
) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BranchOrderDTO, position: Int) {
            binding.orderId.text = item.orderId
            binding.branchName.text = item.branchName
            binding.orderDate.text = item.orderDate
            binding.orderItemCount.text = "${item.orderItemCount}건"
            binding.orderDueDate.text = item.orderDueDate
            binding.orderPrice.text = "금액 ${String.format("%,d", item.orderPrice.toInt())}원"
            binding.orderStatus.text = item.orderStatus

            // 주문 상세 버튼 클릭 시 이벤트 처리
            binding.btnOrderDetail.setOnClickListener {
                val intent = Intent(activity, OrderItemListActivity::class.java)
                intent.putExtra("orderNumber", item.orderId)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
