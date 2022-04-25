package org.hans.myenglishvocabulary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.hans.myenglishvocabulary.databinding.ItemMainDayBinding
import org.hans.myenglishvocabulary.db.DayMemo
import org.hans.myenglishvocabulary.db.ListMemo
import org.hans.myenglishvocabulary.viewmodel.ListViewModel

class MainDayAdapter(private val context: Context, private val viewModelStoreOwner: ViewModelStoreOwner, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MainDayAdapter.ViewHolder>(){
    private var dayData = listOf<DayMemo>()
    private val listData = hashMapOf<DayMemo, List<ListMemo>>()
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(viewModelStoreOwner)[ListViewModel::class.java]
    }

    // 항목 하나를 구성하는 ViewHolder객체 설정
    // 설명 : 그냥 class를 써도 되지만, static 처리가 되어 쓸데없이 메모리를 잡아먹게 되므로(메모리 릭) inner class 사용함.
    inner class ViewHolder(val binding: ItemMainDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(dayMemo: DayMemo) {
            binding.dayIdx.text = "${dayMemo.id}"
            binding.todayDate.text = dayMemo.date

            listViewModel.loadDayAndList().observe(lifecycleOwner, Observer {
                listData.clear()
                listData.putAll(it)
            })

            binding.listSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.listRecyclerView.adapter = MainListAdapter(listData[dayMemo]!!)
                    binding.listRecyclerView.layoutManager = LinearLayoutManager(context)
                } else {
                    binding.listRecyclerView.adapter = null
                }
            }
        }
    }
    // 1. 항목을 구성하는 Item의 총 갯수를 파악할 때 호출되는 메서드
    override fun getItemCount(): Int {
        return dayData.size
    }

    // 2. 항목을 구성하는 Item을 위해 사용할 ViewHolder객체를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // 3. 생성된 ViewHolder객체를 가져와서 현재 위치에 맞는 데이터를 바인딩해주는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(dayData[position])
    }

    fun setData(newData: List<DayMemo>) {
        dayData = newData
        notifyDataSetChanged()
    }
}