package com.bangkit.onikku.ui.recomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.onikku.databinding.ActivityRecomendationBinding
import com.bangkit.onikku.ui.adapter.RecomendAdapter
import com.bangkit.onikku.vmFactory.RecomendationViewModelFactory

class RecomendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecomendationBinding
    private lateinit var recomendViewModel: RecomendationViewModel
    private lateinit var recomendAdapter: RecomendAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPredictResult()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.rvRecomend
        recyclerView.layoutManager = LinearLayoutManager(this)

        recomendAdapter = RecomendAdapter(emptyList())
        recyclerView.adapter = recomendAdapter
    }

    private fun setupViewModel(){
        val factory = RecomendationViewModelFactory.getInstance(this)
        recomendViewModel = ViewModelProvider(this,factory)[RecomendationViewModel::class.java]

        recomendViewModel.recomendLiveData.observe(this, { recomendList ->
            recomendAdapter.updateData(recomendList)
        })

        recomendViewModel.getRecomend()
    }

    private fun setupPredictResult(){
        val predict = intent.getStringExtra(EXTRA_PREDICT)

        binding.textJenisSampah.text = predict
    }

    companion object{
        const val EXTRA_PREDICT = "extra_predict"
    }
}