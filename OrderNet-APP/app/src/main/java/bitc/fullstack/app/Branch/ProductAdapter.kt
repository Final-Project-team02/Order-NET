package bitc.fullstack.app.Branch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.app.R
import bitc.fullstack.app.databinding.ItemProductListBinding
import bitc.fullstack.app.dto.PartsDTO
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// 상품추가 어뎁터
class ProductAdapter(private val items: List<PartsDTO>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PartsDTO) {
            // 이미지 로드 함수 호출
            loadImage(binding.partImg, item.partImg)
            binding.checkboxSelect.isChecked = item.isChecked
            binding.partId.text = item.partId
            binding.partName.text = item.partName
            binding.partCate.text = item.partCate
            binding.txtCount.text = item.count.toString()
            binding.partPrice.text = String.format("%,d원", item.partPrice)

            binding.btnPlus.setOnClickListener {
                item.count++
                binding.txtCount.text =  item.count.toString()
            }

            binding.btnMinus.setOnClickListener {
                if (item.count > 1) {
                    item.count--
                    binding.txtCount.text = item.count.toString()
                }
            }

            // 버튼 뷰 사용 x - _, 생략
            binding.checkboxSelect.setOnCheckedChangeListener { _, checked ->
                // 체크한 상태 저장
                item.isChecked = checked
            }

        }

        // 이미지 로드
        private fun loadImage(imageView: ImageView, url: String?) {
            // 글라이드 이미지 로딩 라이브러리 사용 - 이미지 URL 비동기적 로드
            Glide.with(imageView.context)
                // 이미지 url 로드
                .load(url)
                .apply(
                    RequestOptions()
                        // 로딩 중 출력 이미지
                        .placeholder(R.drawable.noimg)
                        // 실패 시 출력 이미지
                        .error(R.drawable.noimg)
                )
                // 이미지 imageView에 삽입
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}



