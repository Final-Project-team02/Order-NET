package bitc.fullstack.app.Warehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.R
import bitc.fullstack.app.dto.WHDTO

class WHOrderHistoryAdapter(
    private val items: List<WHDTO>
) : RecyclerView.Adapter<WHOrderHistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNumber: TextView = itemView.findViewById(R.id.item_number)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
        val productCodeValue: TextView = itemView.findViewById(R.id.product_code_value)
        val productPriceValue: TextView = itemView.findViewById(R.id.product_price_value)
        val productTotalValue: TextView = itemView.findViewById(R.id.product_total_value)
        val productTotalPriceValue: TextView = itemView.findViewById(R.id.product_total_price_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wh_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemNumber.text = (position + 1).toString()
        holder.productName.text = item.partName  ?: "-"
        holder.categoryName.text = item.partCate ?: "-"
        holder.productCodeValue.text = item.partId
        holder.productPriceValue.text = String.format("%,d", item.partPrice ?: 0)
        holder.productTotalValue.text = item.stockQuantity.toString()
        holder.productTotalPriceValue.text = String.format("%,d", (item.partPrice ?: 0) * item.totalQuantity)
    }

    override fun getItemCount(): Int = items.size
}
