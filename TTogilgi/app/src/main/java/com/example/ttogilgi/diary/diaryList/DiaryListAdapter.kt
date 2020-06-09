package com.example.ttogilgi.diary.diaryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import com.example.ttogilgi.R
import com.example.ttogilgi.diary.ItemViewHolder
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.utils.DiaryDiffUtilCallback
import kotlinx.android.synthetic.main.item_diary.view.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryListAdapter (val event: MutableLiveData<DiaryListEvent> = MutableLiveData()) : ListAdapter<Diary, ItemViewHolder>(
    DiaryDiffUtilCallback()
) {

    private val dateFormat = SimpleDateFormat("MMdd")
    private val weekdayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position).let {
            holder.containerView.summaryView.text = it.content
            holder.containerView.setOnClickListener {
                event.value = DiaryListEvent.OnDiaryItemClick(position)
            }
            holder.containerView.dateView.text = dateFormat.format(it.updatedAt)
            holder.containerView.dayOfTheWeekView.text = weekdayFormat.format(it.updatedAt)
            holder.containerView.tag = it.id
        }
    }

}