package bitc.fullstack.app.Branch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bitc.fullstack.app.R
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityProjectSelectBinding
import bitc.fullstack.app.dto.PartsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectSelectBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var fullList: List<PartsDTO>
    private val productList = mutableListOf<PartsDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(productList)
        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        selectPartsInfo()

        val isEditMode = intent.getBooleanExtra("editMode", false)
        val selectedProduct = intent.getSerializableExtra("selectedProduct") as? PartsDTO

        binding.btnCart.setOnClickListener {
            val selectedItems = productList.filter { it.isChecked }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "선택된 항목이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isEditMode) {
                if (selectedItems.size != 1) {
                    Toast.makeText(this, "하나의 상품만 선택하세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                selectedItems[0]?.let {
                    val resultIntent = Intent().apply {
                        putExtra("selectedProduct", it)
                        putExtra("position", intent.getIntExtra("position", -1))
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                val resultIntent = Intent().apply {
                    putExtra("selectedItems", ArrayList(selectedItems))
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.btnSearch.setOnClickListener {
            val searchQuery = binding.editSearch.text.toString().trim()
            applySearchQuery(searchQuery)
        }

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parentView?.getItemAtPosition(position).toString()
                applyFilters(selectedCategory)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }

    private fun selectPartsInfo() {
        val api = AppServerClass.instance
        val call = api.ProductChoose(null)
        retrofitResponse(call)
    }

    private fun applyFilters(category: String) {
        val filteredList = if (category == "전체") {
            fullList
        } else {
            fullList.filter { it.partCate == category }
        }

        productList.clear()
        productList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

    private fun applySearchQuery(query: String) {
        if (query.isBlank()) {
            productList.clear()
            productList.addAll(fullList)
            adapter.notifyDataSetChanged()
            return
        }

        val filteredList = fullList.filter {
            it.partId.contains(query, ignoreCase = true) ||
                    it.partCate.contains(query, ignoreCase = true) ||
                    it.partName.contains(query, ignoreCase = true)
        }

        productList.clear()
        productList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

    private fun retrofitResponse(call: Call<List<PartsDTO>>) {
        call.enqueue(object : Callback<List<PartsDTO>> {
            override fun onResponse(p0: Call<List<PartsDTO>>, res: Response<List<PartsDTO>>) {
                if (res.isSuccessful) {
                    val partsList = res.body()
                    if (!partsList.isNullOrEmpty()) {
                        fullList = partsList
                        val categories = listOf("전체") + partsList.map { it.partCate }.distinct()

                        // ✅ 커스텀 SpinnerAdapter 적용
                        val spinnerAdapter = object : ArrayAdapter<String>(
                            this@ProjectSelectActivity,
                            R.layout.project_select_spinner_selected_item,
                            categories
                        ) {
                            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                                val view = layoutInflater.inflate(R.layout.project_select_spinner_selected_item, parent, false)
                                val textView = view.findViewById<TextView>(R.id.spinner_selected_text)
                                textView.text = getItem(position)
                                return view
                            }

                            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                                val view = layoutInflater.inflate(R.layout.project_select_spinner_dropdown_item, parent, false)
                                val textView = view.findViewById<TextView>(R.id.spinner_item_text)
                                textView.text = getItem(position)
                                return view
                            }
                        }

                        binding.spinnerCategory.adapter = spinnerAdapter

                        Log.d("csy", "받은 부품 목록: $partsList")
                        productList.clear()
                        productList.addAll(partsList)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
                    res.errorBody()?.let { errorBody ->
                        val error = errorBody.string()
                        Log.d("csy", "Error Response: $error")
                    }
                }
            }

            override fun onFailure(p0: Call<List<PartsDTO>>, t: Throwable) {
                Log.d("csy", "message : ${t.message}")
            }
        })
    }
}
