package com.example.yeoboyaprofile

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yeoboyaprofile.databinding.ItemRecvidolBinding

class AboutAdapter(private var itemList: List<ItemData>) :
    RecyclerView.Adapter<AboutAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecvidolBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData) = with(binding) {
            //이미지 길이 랜덤 설정
            val imgLength = (300..350).random()


            imgFirstName.layoutParams.width = imgLength
            imgFirstName.layoutParams = binding.imgFirstName.layoutParams

            imgSecondName.layoutParams.width = imgLength
            imgSecondName.layoutParams = binding.imgSecondName.layoutParams



            Glide.with(itemView).apply {
                load(item.img_firstName).into(binding.imgFirstName)
                load(item.img_secondName).into(binding.imgSecondName)
            }


            tvFirstName.text = item.tv_firstName

            imgLock.setOnClickListener {
                if (tvUnlockBtn.visibility == View.VISIBLE) {
                    tvUnlockBtn.visibility = View.GONE
                    imgLock.visibility = View.INVISIBLE
                } else {
                    tvUnlockBtn.visibility = View.VISIBLE
                    imgLock.visibility = View.INVISIBLE
                }
            }

            tvUnlockBtn.setOnClickListener {
                tvUnlockBtn.visibility = View.GONE
                imgFirstName.visibility = View.GONE
                imgSecondName.visibility = View.GONE
                tvFirstNameItem.visibility = View.VISIBLE
                tvFirstNameItem.text = item.tv_firstName_item
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val aBinding =
            ItemRecvidolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(aBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])


        //firstName TextView에 대해 커스텀 백그라운드 적용
        val firstName = SpannableString(holder.binding.tvFirstName.text.toString())
        val backColor = ContextCompat.getColor(holder.itemView.context, R.color.primary_200)
        firstName.setSpan(
            CustomBackground(backColor),
            0,
            firstName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.binding.tvFirstName.text = firstName



        if (position >= itemList.size - 3) {
            holder.binding.imgSecondName.apply {
                visibility = View.VISIBLE
                setImageResource(itemList[position].img_secondName)
            }


        } else {
            holder.binding.imgSecondName.visibility = View.GONE
        }
        holder.binding.tvFirstNameItem.text = itemList[position].tv_firstName
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}