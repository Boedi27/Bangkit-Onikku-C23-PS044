package com.bangkit.onikku.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.onikku.data.model.RecomendModel
import com.bangkit.onikku.databinding.ActivityDetailBinding
import com.bangkit.onikku.ui.map.MapsActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        val item =intent.getParcelableExtra<RecomendModel>(EXTRA_RECOMEND)
        binding.apply {
            Glide.with(this@DetailActivity).load(item?.imgOlahan).into(imgDetailOlahan)
            titleOlahan.text = item?.titleOlahan
            txtBahan.text = item?.bahanOlahan
            txtAlat.text = item?.alatOlahan
            txtLangkah.text = item?.langkahOlahan
        }

        binding.btnMaps.setOnClickListener {
            val intent = Intent(this@DetailActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    companion object{
        const val EXTRA_RECOMEND = "extra_recomend"
    }
}