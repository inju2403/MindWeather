package com.example.ttogilgi.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.ttogilgi.model.pojo.Diary

class DiaryDiffUtilCallback : DiffUtil.ItemCallback<Diary>() {

    override fun areItemsTheSame(oldItem: Diary, newItem: Diary) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary) = oldItem.id == newItem.id

}