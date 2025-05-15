package bitc.fullstack.app.Register_Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import bitc.fullstack.app.Branch.BranchMainActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import bitc.fullstack.app.R
import bitc.fullstack.app.Warehouse.WarehouseMainActivity
import bitc.fullstack.app.appserver.AppServerClass
import bitc.fullstack.app.appserver.LoginRequestDto
import bitc.fullstack.app.appserver.LoginResponseDto

class Login : AppCompatActivity() {
    private lateinit var idEditText: EditText
    private lateinit var pwEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var toggleGroup: MaterialButtonToggleGroup
    private lateinit var agencyButton: Button
    private lateinit var centerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        idEditText = findViewById(R.id.btn_id)
        pwEditText = findViewById(R.id.btn_password)
        loginButton = findViewById(R.id.btn_login)
        toggleGroup = findViewById(R.id.toggle_group)


        toggleGroup.clearChecked()

        loginButton.setOnClickListener {
            val userId = idEditText.text.toString()
            val userPw = pwEditText.text.toString()
            val selectedTypeId = toggleGroup.checkedButtonId
            val userType = when (selectedTypeId) {
                R.id.btn_center -> "물류센터"
                R.id.btn_agency -> "대리점"
                else -> ""
            }

            if (userId.isBlank() || userPw.isBlank() || userType.isBlank()) {
                Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequestDto(userId, userPw, userType)

            AppServerClass.instance.login(loginRequest).enqueue(object : Callback<LoginResponseDto> {
                override fun onResponse(call: Call<LoginResponseDto>, response: Response<LoginResponseDto>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        loginResponse?.let {
                            // 토큰 저장 (SharedPreferences)
                            val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                            prefs.edit().putString("token", it.token).apply()

                            Toast.makeText(this@Login, "${it.userType} 로그인 성공", Toast.LENGTH_SHORT).show()

                            // 대리점 로그인 시 대리점 메인 화면으로 이동
//                            val intent =  Intent(this@Login, BranchMainActivity::class.java).apply {
//                                putExtra("userType", it.userType)
//                                putExtra("userRefId", it.userRefId)
//                                putExtra("branchSupervisor", it.branchSupervisor)
//                                putExtra("warehouseName", it.warehouseName)
//                                putExtra("branchName", it.branchName)
//                            }
//                            startActivity(intent)
//                            finish()

                            when (it.userType) {
                                "대리점" -> {
                                    val intent = Intent(this@Login, BranchMainActivity::class.java).apply {
                                        putExtra("userType", it.userType)
                                        putExtra("userRefId", it.userRefId)
                                        putExtra("branchSupervisor", it.branchSupervisor)
                                        putExtra("warehouseName", it.warehouseName)
                                        putExtra("branchName", it.branchName)
                                    }
                                    startActivity(intent)
                                    finish()
                                }

                                "물류센터" -> {
                                    val intent = Intent(this@Login, WarehouseMainActivity::class.java).apply {
                                        putExtra("userType", it.userType)
                                        putExtra("userRefId", it.userRefId)
                                        putExtra("warehouseName", it.warehouseName)
                                    }
                                    startActivity(intent)
                                    finish()
                                }

                                else -> {
                                    Toast.makeText(this@Login, "알 수 없는 사용자 유형입니다.", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }

                    } else {
                        // 실패 사유 출력
                        val errorMessage = response.errorBody()?.string()
                        Toast.makeText(this@Login, "로그인 실패: $errorMessage", Toast.LENGTH_LONG).show()
                        Log.d("Login", "로그인 실패: $errorMessage")

                    }
                }

                override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                    Toast.makeText(this@Login, "서버 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

//package bitc.fullstack.app.Register_Login
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import bitc.fullstack.app.R
//import bitc.fullstack.app.databinding.ActivityLoginBinding
//
//class Login : AppCompatActivity() {
//    private val binding: ActivityLoginBinding by lazy {
//        ActivityLoginBinding.inflate(layoutInflater)
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}