package bitc.fullstack.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.databinding.ActivityMainBinding
import bitc.fullstack.app.Warehouse.OrderDetailActivity
import bitc.fullstack.app.Branch.BranchProductSelect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(binding.root)
    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    selectTest()

      binding.selectbutton.setOnClickListener {
    val intent = Intent(this@MainActivity, BranchProductSelect::class.java)
    startActivity(intent)
    finish()
  }

  binding.detailbutton.setOnClickListener {
    val intent = Intent(this@MainActivity, OrderDetailActivity::class.java)
    startActivity(intent)
    finish()
  }
  }

  private fun selectTest() {
    val api = AppServerClass.instance
    val call = api.serverTest()
    retrofitResponse(call)
  }

  private fun retrofitResponse(call: Call<String>) {
    call.enqueue(object : Callback<String> {
      override fun onResponse(p0: Call<String>, res: Response<String>) {
        if (res.isSuccessful) {

          val fullList = res.body()
          Log.d("csy", "result : $fullList")

        }
        else {
          Log.d("csy", "송신 실패, 상태 코드: ${res.code()}, 메시지: ${res.message()}")
          res.errorBody()?.let { errorBody ->
            val error = errorBody.string()
            Log.d("csy", "Error Response: $error")
          }
        }
      }

      override fun onFailure(p0: Call<String>, t: Throwable) {
        Log.d("csy", "message : $t.message")
      }
    })
  }
}