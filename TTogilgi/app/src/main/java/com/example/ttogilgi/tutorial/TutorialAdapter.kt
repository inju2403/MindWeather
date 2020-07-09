package com.example.ttogilgi.tutorial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ttogilgi.R

class TutorialAdapter(private var pageList: ArrayList<PageItem>)
    :RecyclerView.Adapter<PagerViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).
            inflate(R.layout.layout_tutorial_item, parent, false))
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindWithView(pageList[position])
    }

}