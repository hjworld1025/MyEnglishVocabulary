package org.hans.myenglishvocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import org.hans.myenglishvocabulary.databinding.ItemCardBinding
import org.hans.myenglishvocabulary.db.ListMemo

class CardAdapter(private val seekBar: SeekBar?) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    private var listData = listOf<ListMemo>()
    private var openType = true

    inner class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(listMemo: ListMemo) {
            binding.cardViewText.text = listMemo.english
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        viewHolder.binding.cardViewText.setOnClickListener() {
            if (openType) {
                binding.cardViewText.text = listData[viewHolder.adapterPosition].korean
                openType = false
            } else {
                binding.cardViewText.text = listData[viewHolder.adapterPosition].english
                openType = true
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listData[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.binding.cardViewText.text = listData[position].korean
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setData(newData: List<ListMemo>) {
        listData = newData
        notifyDataSetChanged()

        if (seekBar != null) {
            seekBar.max = listData.size - 1
        }
    }

    fun openCard(position: Int) {
        if (openType) {
            notifyItemChanged(position, "click")
            openType = false
        } else {
            notifyItemChanged(position)
            openType = true
        }
    }

    fun defaultCard(position: Int) {
        notifyItemChanged(position)
        openType = true
    }
}