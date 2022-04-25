package org.hans.myenglishvocabulary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.hans.myenglishvocabulary.databinding.ItemMainListBinding
import org.hans.myenglishvocabulary.db.ListMemo

class MainListAdapter(private var listData: List<ListMemo>) : RecyclerView.Adapter<MainListAdapter.ViewHolderClass>() {
    // 항목 하나를 구성하는 ViewHolder객체를 설정한다.
    // 설명 : 그냥 class를 써도 되지만, static 처리가 되어 쓸데없이 메모리를 잡아먹게 되므로(메모리 릭) inner class 사용함.
    inner class ViewHolderClass(val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(listMemo: ListMemo) {
            binding.englishWord.text = listMemo.english
            binding.koreanWord.text = listMemo.korean
        }
    }

    // 1. 항목을 구성하는 Item의 총 갯수를 파악할 때 호출되는 메서드
    override fun getItemCount(): Int {
        return listData.size
    }

    // 2. 항목을 구성하는 Item을 위해 사용할 ViewHolder객체를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderClass(binding)
    }

    // 3. 생성된 ViewHolder객체를 가져와서 현재 위치에 맞는 데이터를 바인딩해주는 메서드
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.onBind(listData[position])
    }

    fun setData(newData: List<ListMemo>) {
        listData = newData
        notifyDataSetChanged()
    }
}
