package com.touhidapps.firemeet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.touhidapps.firemeet.R
import com.touhidapps.firemeet.model.NewsModel

class NewsAdapter(
    val context: Context,
    val newsList: ArrayList<NewsModel>,
    val newsInterface: NewsInterface,
    val updateInteface: UpdateInteface
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    class  DataViewHolder(itemView: View) : ViewHolder(itemView)
    {
        var imgNews = itemView.findViewById<ImageView>(R.id.imgNews)
        var imgUpdate = itemView.findViewById<ImageView>(R.id.imgUpdate)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtDesc = itemView.findViewById<TextView>(R.id.txtDesc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
        return  DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  newsList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.txtDesc.text = newsList.get(position).desc
        holder.txtTitle.text = newsList.get(position).title
        holder.txtTitle.text = newsList.get(position).title

        holder.imgDelete.setOnClickListener {
            newsInterface.deleteClick(newsList.get(position).key);
        }

        holder.imgUpdate.setOnClickListener {
            updateInteface.onClick(newsList.get(position))
        }

        Glide.with(context).load(newsList.get(position).image).into(holder.imgNews)
    }

}