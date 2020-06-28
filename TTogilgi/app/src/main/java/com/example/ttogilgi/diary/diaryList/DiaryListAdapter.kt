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
    private val timeFormat = SimpleDateFormat("HH:mm")
    private val weekdayFormat = SimpleDateFormat("EEE", Locale.ENGLISH)

    private var largestId = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position).let {
            var diaryId = it?.id
            holder.containerView.summaryView.text = it.content
            holder.containerView.setOnClickListener {
                event.value = DiaryListEvent.OnDiaryItemClick(diaryId!!)
            }
            holder.containerView.dateView.text = dateFormat.format(it.updatedAt)
            holder.containerView.timeView.text = timeFormat.format(it.updatedAt)
            holder.containerView.dayOfTheWeekView.text = weekdayFormat.format(it.updatedAt)
            holder.containerView.tag = it.id

            if(largestId < diaryId!!.toInt()) {
                largestId = diaryId!!.toInt()
                event.value = DiaryListEvent.OnDiaryCreated(largestId)
            }

            //감정 표정 세팅
            if(it.happiness==1) {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_happiness)
            }
            else if(it.sadness==1) {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_sadness)
            }
            else if(it.worry==1) {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_worry)
            }
            else if(it.anger==1) {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_anger)
            }
            else if(it.neutrality==1) {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_neatrality)
            }
            else {
                holder.containerView.diaryCardImage.setImageResource(R.drawable.ic_unknowability)
            }
        }
    }

}