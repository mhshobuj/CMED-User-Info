package com.mhs.userinfo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhs.userinfo.adapter.UserAdapter
import com.mhs.userinfo.databinding.ActivityMainBinding
import com.mhs.userinfo.utils.DataStatus
import com.mhs.userinfo.utils.initRecycler
import com.mhs.userinfo.utils.isVisible
import com.mhs.userinfo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        lifecycleScope.launch {
            binding.apply {
                viewModel.getUserList()
                viewModel.usersList.observe(this@MainActivity){ it ->
                    when(it.status){
                        DataStatus.Status.LOADING->{
                            pBarLoading.isVisible(true, rvUserLst)
                        }
                        DataStatus.Status.SUCCESS->{
                            pBarLoading.isVisible(false, rvUserLst)
                            userAdapter.differ.submitList(it.data)
                            userAdapter.setOnClickListener {
                                val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                                intent.putExtra("ID", it.id)
                                startActivity(intent)
                            }
                        }
                        DataStatus.Status.ERROR->{
                            pBarLoading.isVisible(false, rvUserLst)
                            Toast.makeText(this@MainActivity, "There is something wrong!!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    private fun setUpRecyclerView() {
        binding.rvUserLst.initRecycler(LinearLayoutManager(this), userAdapter)
    }

}