package com.example.assessmentfetchapi.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.assessmentfetchapi.R
import com.example.assessmentfetchapi.databinding.ActivityDetailBinding
import com.example.assessmentfetchapi.db.model.UserEntity
import com.example.assessmentfetchapi.utils.StatusBarManager

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view =  binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        StatusBarManager.changeStatusBarColor(this , "#03A9F4")

        val user : UserEntity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("userData", UserEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra("userData")
        }

        user?.let {
            binding.uName.text = it.name
            binding.uCompName.text = it.company
            binding.userName.text = it.username
            binding.uEmail.text = it.email
            binding.uAddress.text = it.address
            binding.uZip.text = it.zip
            binding.uState.text = it.state
            binding.uCountry.text = it.country
            binding.uPhone.text = it.phone

            Glide.with(this)
                .load(it.photo)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error_placeholder)
                .into(binding.uImg)
        }


        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}