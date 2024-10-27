package com.example.assessmentfetchapi.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessmentfetchapi.utils.OnItemClick
import com.example.assessmentfetchapi.R
import com.example.assessmentfetchapi.adapter.UsersAdapter
import com.example.assessmentfetchapi.api.NetworkResult
import com.example.assessmentfetchapi.databinding.ActivityMainBinding
import com.example.assessmentfetchapi.db.model.UserEntity
import com.example.assessmentfetchapi.utils.NetworkUtils
import com.example.assessmentfetchapi.utils.StatusBarManager
import com.example.assessmentfetchapi.viewmodel.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : UsersAdapter
    private val usersViewModel : UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view =  binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        StatusBarManager.changeStatusBarColor(this , "#03A9F4")

        setupRecyclerView()
        getUsersFromApi()


    }

    private fun getUsersFromApi() {
        if (NetworkUtils.isInternetAvailable(this)){
//            binding.noConnectinTxt.visibility = View.GONE
            usersViewModel.usersResponseApi.observe(this){ result ->
                when(result){
                    is NetworkResult.Loading ->{
                        binding.progress.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success ->{
                        binding.progress.visibility = View.GONE
                        Log.d("TAG", "getUsersFromApi=== : ${result.body}")
//                    result.body?.let { users ->
//                        adapter.updateData(users)
//                    }
                    }
                    is NetworkResult.Error -> {
                        binding.progress.visibility = View.GONE
                        showSnackbar(binding.main, result.message.toString())
                    }
                    else ->{}
                }
            }
            usersViewModel.getUsersApi()
        }else{
//            binding.noConnectinTxt.visibility = View.VISIBLE
            showSnackbar(binding.main,"No Internet Connection 😑!")
        }



    }
    private fun showSnackbar(view: View, message:String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)

        snackbar.setAction("Close") { snackbar.dismiss() }
        snackbar.setActionTextColor(Color.RED)
        snackbar.setTextColor(Color.WHITE)
        snackbar.setBackgroundTint(Color.BLACK)
        snackbar.show()
    }

    private fun setupRecyclerView() {

        usersViewModel.usersResponseRoomDb.observe(this) { users -> // get users from roomDb
            if (users.isEmpty() && !NetworkUtils.isInternetAvailable(this)){
                binding.noConnectinTxt.visibility = View.VISIBLE
            }else{
                binding.noConnectinTxt.visibility = View.GONE
                adapter = UsersAdapter(users,this)
                binding.rcv.adapter = adapter
                binding.rcv.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    override fun onItemClickListener(userEntity: UserEntity) {
        val intent  = Intent(this , DetailActivity::class.java)
        intent.putExtra("userData" , userEntity)
        startActivity(intent)
    }
}