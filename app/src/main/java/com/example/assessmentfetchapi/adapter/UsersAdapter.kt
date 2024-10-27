package com.example.assessmentfetchapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assessmentfetchapi.utils.OnItemClick
import com.example.assessmentfetchapi.R
import com.example.assessmentfetchapi.databinding.UserItemBinding
import com.example.assessmentfetchapi.db.model.UserEntity

class UsersAdapter(
    private val list: List<UserEntity>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        val users  = list[position]

        holder.binding.uName.text = users.name
        holder.binding.uCompName.text = users.company

        Glide.with(holder.itemView.context)
            .load(users.photo)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error_placeholder)
            .into(holder.binding.uImg)

        holder.binding.detailBtn.setOnClickListener {
            onItemClick.onItemClickListener(users)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}