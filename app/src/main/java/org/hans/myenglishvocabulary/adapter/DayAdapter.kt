package org.hans.myenglishvocabulary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import org.hans.myenglishvocabulary.R
import org.hans.myenglishvocabulary.databinding.ItemDayBinding
import org.hans.myenglishvocabulary.db.DayMemo

class DayAdapter(private val context: Context, private val dayData: List<DayMemo>) : BaseAdapter() {
    override fun getCount(): Int {
        return dayData.size
    }

    override fun getItem(position: Int): Any {
        return dayData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ItemDayBinding?

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_day, parent, false)
        } else {
            binding = DataBindingUtil.getBinding(convertView)
        }

        binding!!.model = dayData[position]

        return binding!!.root
    }
}