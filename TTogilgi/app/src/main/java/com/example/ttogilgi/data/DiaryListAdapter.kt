package com.example.ttogilgi.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ttogilgi.R
import kotlinx.android.synthetic.main.item_diary.view.*
import java.text.SimpleDateFormat
import java.util.*

class DiaryListAdapter (private val list : MutableList<DiaryData>) : RecyclerView.Adapter<ItemViewHolder> () {

    private val dateFormat = SimpleDateFormat("MMdd")
    private val weekdayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)

    lateinit var itemClickListener : (itemId : String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        itemView.setOnClickListener {
            itemClickListener?.run {
                val diaryId = it.tag as String
                this(diaryId)
            }
        }
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.containerView.summaryView.text = list[position].summary
        holder.containerView.dateView.text = dateFormat.format(list[position].createdAt)
        holder.containerView.dayOfTheWeekView.text = weekdayFormat.format(list[position].createdAt)
        holder.containerView.tag = list[position].id
    }

}