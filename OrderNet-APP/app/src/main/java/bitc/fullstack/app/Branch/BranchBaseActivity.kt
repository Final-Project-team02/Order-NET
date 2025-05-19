package bitc.fullstack.app.Branch

import android.content.Intent
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login
import bitc.fullstack.app.Warehouse.WHMainActivity

abstract class BranchBaseActivity : AppCompatActivity() {

    // 툴바 설정 함수 (자식 액티비티에서 호출)
    protected fun setupToolbar(menuButton: ImageButton, homeButton: ImageButton? = null) {
        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.branch_header_menu, popupMenu.menu)

            onPreparePopupMenu(popupMenu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_order -> {
                        val branchId = intent.getStringExtra("userRefId") ?: ""
                        val intent = Intent(this, BranchOrderResiActivity::class.java)
                        intent.putExtra("userRefId", branchId)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_stock -> {
                        val branchId = intent.getStringExtra("userRefId") ?: ""
                        val intent = Intent(this, OrderHistoryActivity::class.java)
                        intent.putExtra("userRefId", branchId)
                        startActivity(intent)
                        true
                    }

                    R.id.btn_logout -> {
                        // 로그아웃
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().clear().apply()
                        val intent = Intent(this, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        // 홈 버튼 처리 (필요한 경우에만)
        homeButton?.setOnClickListener {
            // 현재 액티비티가 WHMainActivity이면 아무 것도 하지 않음
            if (this::class.java == BranchMainActivity::class.java) {
                Toast.makeText(this, "현재 홈 화면입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, BranchMainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
        }
    }

    /**
     * 메뉴 생성 후 커스터마이징할 수 있는 함수
     * 자식 액티비티에서 override 가능
     */
    protected open fun onPreparePopupMenu(popupMenu: PopupMenu) {
        // 기본은 아무 것도 안 함
    }
}

