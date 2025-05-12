package bitc.fullstack.app.Branch

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.databinding.ItemProductBinding
import bitc.fullstack.app.dto.PartsDTO

class SelectedProductAdapter(    private val items: MutableList<PartsDTO>,
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

            // 상품 삭제 버튼
            binding.productDelete.setOnClickListener {
                // pos - 몇번째 아이템인지 위치 변수
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    // 해당 위치 아이템 제거
                    items.removeAt(pos)
                    // recyclerView 해당 아이템 삭제 전송
                    notifyItemRemoved(pos)
                    // 삭제된 아이템 만큼 아이템 번호 갱신
                    notifyItemRangeChanged(pos, items.size)
                    // 금액 계산
                    updateTotalAmount()
                }
            }
            
            // 상품 변경 버튼
            binding.productChange.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    // 선택된 상품을 Intent로 전달하고, ProjectSelectActivity로 이동
                    val selectedItem = items[pos] // 현재 선택된 상품 하나
                    val intent = Intent(binding.root.context, ProjectSelectActivity::class.java)
                    intent.putExtra("editMode", true) // 편집 모드
                    intent.putExtra("selectedProduct", selectedItem) // 수정할 상품 전달
                    intent.putExtra("position", pos)

                    // ActivityResultLauncher를 통해 ProjectSelectActivity 실행
                    (binding.root.context as? BranchOrderResiActivity)?.let {
                        it.selectProductLauncher.launch(intent) // ActivityResultLauncher를 사용하여 결과 받을 수 있도록 시작
                    }
                }
            }
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

    // 아이템 교체 메소드
    fun updateItem(position: Int, newItem: PartsDTO) {
        items[position] = newItem
        notifyItemChanged(position)
    }
}
