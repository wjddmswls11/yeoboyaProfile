package com.example.yeoboyaprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yeoboyaprofile.databinding.ItemRecvidolBinding

class AboutAdapter(private var itemList: List<ItemData>) : RecyclerView.Adapter<AboutAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecvidolBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgSecond: ImageView = binding.imgSecondName
        val imgLock : ImageView = binding.imgLock
        val view_firstName : ConstraintLayout = binding.viewFirstName
        val tv_firstName_item : TextView = binding.tvFirstNameItem
        val tv_secondName_item : TextView = binding.tvSecondNameItem

        fun bind(item: ItemData) {
            Glide.with(itemView).apply {
                load(item.img_firstName).into(binding.imgFirstName)
                load(item.img_secondName).into(imgSecond)
            }
            binding.tvFirstName.text = item.tv_firstName

            imgLock.setOnClickListener {
                if (view_firstName.visibility == View.VISIBLE) {
                    view_firstName.visibility = View.GONE
                    imgLock.visibility = View.INVISIBLE
                } else {
                    view_firstName.visibility = View.VISIBLE
                    imgLock.visibility = View.INVISIBLE
                }
            }

            view_firstName.setOnClickListener{
                view_firstName.visibility = View.GONE
                binding.imgFirstName.visibility = View.GONE
                binding.imgSecondName.visibility = View.GONE
                binding.tvFirstNameItem.visibility = View.VISIBLE
                binding.tvFirstNameItem.text = item.tv_firstName_item

                if (adapterPosition >= itemList.size - 3) {
                    binding.tvSecondNameItem.visibility = View.VISIBLE
                    binding.tvSecondNameItem.text = item.tv_secondName_item
                } else {
                    binding.tvSecondNameItem.visibility = View.GONE
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val aBinding = ItemRecvidolBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(aBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])

        if (position >= itemList.size -3) {
            holder.binding.imgSecondName.visibility = View.VISIBLE
            holder.binding.imgSecondName.setImageResource(itemList[position].img_secondName)
        } else {
            holder.binding.imgSecondName.visibility = View.GONE
        }
        holder.binding.tvFirstNameItem.text = itemList[position].tv_firstName
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}