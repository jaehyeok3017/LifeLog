package com.careerproject.lifelog.ui.main.check

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.careerproject.lifelog.R
import com.careerproject.lifelog.data.news.Data
import com.careerproject.lifelog.data.news.NewsData

class NewsAdapter (private val recyclerViewItems: ArrayList<Data>):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(recyclerViewItems[position])
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.item_name)
        private var company: TextView = itemView.findViewById(R.id.item_company)
        private var text: TextView = itemView.findViewById(R.id.item_text)

        @SuppressLint("SetTextI18n")
        fun bind(recyclerViewItem: Data) {
            title.text = recyclerViewItem.title
            company.text = recyclerViewItem.company
            text.text = recyclerViewItem.text
        }
    }
}