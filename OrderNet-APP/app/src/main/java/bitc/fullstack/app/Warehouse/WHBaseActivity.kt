package bitc.fullstack.app.Warehouse
import android.content.Intent
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bitc.fullstack.app.R
import bitc.fullstack.app.Register_Login.Login

abstract class WHBaseActivity : AppCompatActivity() {

    // 툴바 설정 함수 (자식 액티비티에서 호출)
    protected fun setupToolbar(menuButton: ImageButton, homeButton: ImageButton? = null) {


        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.wh_header_menu, popupMenu.menu)

            onPreparePopupMenu(popupMenu)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.menu_stock -> {
                        Toast.makeText(this, "재고현황", Toast.LENGTH_SHORT).show()
                        val warehouseId = intent.getStringExtra("userRefId") ?: "" //  userRefId 재사용
                        val intent = Intent(this, WHOrderHistory::class.java)
                        intent.putExtra("userRefId", warehouseId) // userRefId 전달
                        startActivity(intent)
                        true
                    }
                    R.id.btn_logout -> {
                        // 1. 저장된 값 삭제
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().clear().apply()

                        // 2. 로그인 화면으로 이동
                        val intent = Intent(this, Login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        // 3. 현재 액티비티 종료
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
            if (this::class.java == WHMainActivity::class.java) {
                Toast.makeText(this, "현재 홈 화면입니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, WHMainActivity::class.java)
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