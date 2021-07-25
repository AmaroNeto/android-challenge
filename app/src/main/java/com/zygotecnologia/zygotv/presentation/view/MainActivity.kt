package com.zygotecnologia.zygotv.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zygotecnologia.zygotv.R
import com.zygotecnologia.zygotv.databinding.ActivityMainBinding
import com.zygotecnologia.zygotv.domain.model.Show
import com.zygotecnologia.zygotv.presentation.adapter.MainAdapter
import com.zygotecnologia.zygotv.presentation.gateway.MainViewModel
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpObservers()
        setRecycleView()
    }

    private fun setUpObservers() {
        viewModel.popularShows.observe(this) {
            setPopularShows(it)
        }
    }

    private fun setRecycleView() {
        binding.rvShowList.apply {
            layoutManager =  LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            setHasFixedSize(true)
        }
    }

    private fun setPopularShows(shows: List<Show>) {
        binding.rvShowList.adapter = MainAdapter(shows)
    }

    private fun loadShows() {
        viewModel.loadShows()
    }

    override fun onResume() {
        super.onResume()
        loadShows()
    }
}