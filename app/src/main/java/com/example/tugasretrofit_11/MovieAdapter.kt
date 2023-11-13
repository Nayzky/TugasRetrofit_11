// MovieAdapter.kt
package com.example.tugasretrofit_11

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugasretrofit_11.databinding.ActivityMovieAdapterBinding
import kotlin.collections.ArrayList

class MovieAdapter(
    var results: ArrayList<MainModel.Result>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityMovieAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.binding.textView.text = result.title
        Glide.with(holder.binding.root)
            .load(result.image)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(holder.binding.imageView)
        holder.binding.root.setOnClickListener { listener.onClick(result, holder.binding.root.context) }
    }

    class ViewHolder(val binding: ActivityMovieAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<MainModel.Result>) {
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: MainModel.Result, context: Context)
    }
}
