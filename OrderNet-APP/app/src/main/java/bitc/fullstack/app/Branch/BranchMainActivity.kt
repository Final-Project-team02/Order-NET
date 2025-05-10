package bitc.fullstack.app.Branch

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bitc.fullstack.app.databinding.ActivityBranchMainBinding

class BranchMainActivity : AppCompatActivity() {

    private val binding: ActivityBranchMainBinding by lazy {
        ActivityBranchMainBinding.inflate(layoutInflater)
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
        binding.orderButton.setOnClickListener {
            val intent = Intent(this, BranchOrderResiActivity::class.java)
            startActivity(intent)
        }
    }
}