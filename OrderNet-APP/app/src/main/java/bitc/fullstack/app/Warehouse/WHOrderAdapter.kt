package bitc.fullstack.app.Warehouse

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.R
import bitc.fullstack.app.dto.OrderAppDTO
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class WHOrderAdapter(
    private var orderList: List<OrderAppDTO>,
    private val userRefId: String // userRefId 전달
) : RecyclerView.Adapter<WHOrderAdapter.WHOrderViewHolder>() {

    class WHOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderNumber: TextView = itemView.findViewById(R.id.tv_order_number)
        val tvStoreName: TextView = itemView.findViewById(R.id.tv_branch_name)
        val tvOrderDate: TextView = itemView.findViewById(R.id.tv_order_date)
        val tvOrderCount: TextView = itemView.findViewById(R.id.tv_order_count)
        val tvOrderPrice: TextView = itemView.findViewById(R.id.tv_order_item_price)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tv_order_item_status)
        val btnOrderDetail: Button = itemView.findViewById(R.id.btn_order_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WHOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_warehouse_order, parent, false)
        return WHOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: WHOrderViewHolder, position: Int) {
        val order = orderList[position]

        // 주문 정보 표시
        holder.tvOrderNumber.text = "주문번호 ${order.orderId}"
        holder.tvStoreName.text = order.branchName ?: "지점 정보 없음"
        holder.tvOrderDate.text = order.orderDate?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
        } ?: "날짜 없음"

        // 주문에 포함된 품목들의 수량을 합산해서 표시
        holder.tvOrderCount.text = "${order.totalQuantity}건"

        // 가격 및 상태 표시
        holder.tvOrderPrice.text = order.warehouseOrderPrice?.let {
            "금액 ${NumberFormat.getNumberInstance(Locale.KOREA).format(it)} 원"
        } ?: "금액 정보 없음"

        holder.tvOrderStatus.text = order.orderItemStatus

        // 주문 상세 페이지로 이동
        holder.btnOrderDetail.setOnClickListener {
            val context = it.context
            val intent = Intent(context, OrderDetailActivity::class.java).apply {
                putExtra("order_id", order.orderId)
                putExtra("userRefId", userRefId)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun updateData(newList: List<OrderAppDTO>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
