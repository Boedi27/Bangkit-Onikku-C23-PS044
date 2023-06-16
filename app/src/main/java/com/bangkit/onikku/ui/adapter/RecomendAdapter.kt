package com.bangkit.onikku.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.onikku.data.model.RecomendModel
import com.bangkit.onikku.databinding.ItemRowRecomendBinding
import com.bangkit.onikku.ui.detail.DetailActivity
import com.bumptech.glide.Glide

class RecomendAdapter(private var recomendList: List<RecomendModel>) :
    RecyclerView.Adapter<RecomendAdapter.RecomendViewHolder>() {

    inner class RecomendViewHolder(private val binding: ItemRowRecomendBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: RecomendModel){
                Glide.with(itemView.context).load(item.imgOlahan).into(binding.imgOlahan)
                binding.txtTitleOlahan.text = item.titleOlahan

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_RECOMEND, item)
                    itemView.context.startActivity(intent)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendAdapter.RecomendViewHolder {
        val binding = ItemRowRecomendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecomendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecomendAdapter.RecomendViewHolder, position: Int) {
        val item = recomendList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return recomendList.size
    }

    fun updateData(newRecomendList: List<RecomendModel>) {
        recomendList = newRecomendList
        notifyDataSetChanged()
    }
}