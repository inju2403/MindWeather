package com.example.mindWeather.tutorial

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_tutorial_item.view.*

class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val itemImage = itemView.tutorial_imageView

    fun bindWithView(pageItem: PageItem) {
        itemImage.setImageResource(pageItem.imageSrc)
    }
}