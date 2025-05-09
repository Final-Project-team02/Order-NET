package bitc.fullstack.app.Warehouse

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.R
import bitc.fullstack.app.dto.OrderAppDTO
import java.text.SimpleDateFormat

class WHOrderAdapter(
    private var orderList: List<OrderAppDTO>
) : RecyclerView.Adapter<WHOrderAdapter.WHOrderViewHolder>() {

    // ViewHolder
    class WHOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderNumber: TextView       = itemView.findViewById(R.id.tv_order_number)
        val tvStoreName: TextView         = itemView.findViewById(R.id.tv_branch_name)
        val tvOrderDate: TextView         = itemView.findViewById(R.id.tv_order_date)
        val tvOrderCount: TextView        = itemView.findViewById(R.id.tv_order_count)
        val tvOrderPrice: TextView        = itemView.findViewById(R.id.tv_order_item_price)
        val tvOrderStatus: TextView       = itemView.findViewById(R.id.tv_order_item_status)
        val btnOrderDetail: Button        = itemView.findViewById(R.id.btn_order_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WHOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_warehouse_order, parent, false)
        return WHOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: WHOrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.tvOrderNumber.text = "주문번호 ${order.orderId}"
        holder.tvStoreName.text   = order.branchName ?: "지점 정보 없음"

        // 날짜 널 체크
        holder.tvOrderDate.text = order.orderDate?.let {
            SimpleDateFormat("yyyy-MM-dd").format(it)
        } ?: "날짜 없음"

        holder.tvOrderCount.text = "${order.orderItemQuantity}건"

        // 가격 널 체크
        holder.tvOrderPrice.text = order.orderItemPrice?.let {
            "금액 ${it.toPlainString()}"
        } ?: "금액 정보 없음"

        holder.tvOrderStatus.text = order.orderItemStatus ?: "상태 없음"

        holder.btnOrderDetail.setOnClickListener {
            val ctx = it.context
            val intent = Intent(ctx, OrderDetailActivity::class.java).apply {
                putExtra("orderId", order.orderId)
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orderList.size

    /** 외부에서 데이터만 교체할 때 사용 */
    fun updateData(newList: List<OrderAppDTO>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
