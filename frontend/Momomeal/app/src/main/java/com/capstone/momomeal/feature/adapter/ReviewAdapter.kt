package com.capstone.momomeal.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.momomeal.R
import com.capstone.momomeal.databinding.ItemReviewContentBinding
import com.capstone.momomeal.data.Rate
import com.capstone.momomeal.data.Review

class ReviewAdapter(
    var reviewList: ArrayList<Review>
) : RecyclerView.Adapter<ViewHolder>() {
    fun replaceData(reviews: List<Review>){
        reviewList = ArrayList<Review>(reviews)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }
}

class ViewHolder(val binding: ItemReviewContentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Review) {
        binding.tvReviewContent.text = item.conReview
        when (item.rateSign) {
            Rate.LIKE -> binding.ivReviewContent.setImageResource(R.drawable.ic_good_sign)
            Rate.BAD -> binding.ivReviewContent.setImageResource(R.drawable.ic_bad_sign)
        }
    }
}