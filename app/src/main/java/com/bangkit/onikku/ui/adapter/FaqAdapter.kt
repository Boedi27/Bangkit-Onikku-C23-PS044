package com.bangkit.onikku.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.onikku.data.model.FaqModel
import com.bangkit.onikku.databinding.ItemRowFaqBinding

class FaqAdapter(private val faqList: List<FaqModel>) :
    RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    private val collapsedStates = mutableMapOf<Int, Boolean>()

    class ViewHolder(private val binding: ItemRowFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FaqModel, isCollapsed: Boolean) {
            binding.txtJudulFaq.text = faq.titleFaq
            binding.txtDescFaq.text = faq.descFaq

            // Menampilkan/menyembunyikan teks deskripsi dan ikon sesuai keadaan collapsed
            if (isCollapsed) {
                binding.txtDescFaq.visibility = View.GONE
                binding.imgArrowDown.visibility = View.VISIBLE
                binding.imgArrowUp.visibility = View.GONE
            } else {
                binding.txtDescFaq.visibility = View.VISIBLE
                binding.imgArrowDown.visibility = View.GONE
                binding.imgArrowUp.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowFaqBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faq = faqList[position]
        val isCollapsed = collapsedStates[position] ?: true

        holder.bind(faq, isCollapsed)

        holder.itemView.setOnClickListener {
            collapsedStates[position] = !isCollapsed
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}