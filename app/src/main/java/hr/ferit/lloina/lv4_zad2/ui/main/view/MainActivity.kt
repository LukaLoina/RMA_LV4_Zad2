package hr.ferit.lloina.lv4_zad2.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hr.ferit.lloina.lv4_zad2.R
import hr.ferit.lloina.lv4_zad2.ui.main.adapter.MainAdapter
import hr.ferit.lloina.lv4_zad2.ui.main.viewModel.MainViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.lloina.lv4_zad2.data.api.ApiHelper
import hr.ferit.lloina.lv4_zad2.data.api.ApiServiceImpl
import hr.ferit.lloina.lv4_zad2.data.model.User
import hr.ferit.lloina.lv4_zad2.databinding.ActivityMainBinding
import hr.ferit.lloina.lv4_zad2.ui.base.ViewModelFactory
import hr.ferit.lloina.lv4_zad2.utils.Status

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(ApiServiceImpl())))
                                          .get(MainViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context,
            (binding.recyclerView.layoutManager as LinearLayoutManager).orientation))
        binding.recyclerView.adapter = adapter
    }
}