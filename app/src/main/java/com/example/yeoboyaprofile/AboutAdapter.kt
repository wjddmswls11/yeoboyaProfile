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
   /*     val imgSecond: ImageView = binding.imgSecondName
        val view_firstName : ConstraintLayout = binding.viewFirstName
*/
        fun bind(item: ItemData) = with(binding) {
            //이미지 길이 랜덤 설정
            val imgLength = (300..430).random()

            val layoutParams = binding.imgFirstName.layoutParams
            layoutParams.width = imgLength
            imgFirstName.layoutParams = layoutParams

            val layoutParams2 = binding.imgSecondName.layoutParams
            layoutParams2.width = imgLength
            imgSecondName.layoutParams = layoutParams2


            Glide.with(itemView).apply {
                load(item.img_firstName).into(binding.imgFirstName)
                load(item.img_secondName).into(binding.imgSecondName)
            }

            binding.apply {
                tvFirstName.text = item.tv_firstName

                imgLock.setOnClickListener {
                    if (binding.viewFirstName.visibility == View.VISIBLE) {
                        binding.viewFirstName.visibility = View.GONE
                        imgLock.visibility = View.INVISIBLE
                    } else {
                        binding.viewFirstName.visibility = View.VISIBLE
                        imgLock.visibility = View.INVISIBLE
                    }
                }

                viewFirstName.setOnClickListener{
                    binding.apply {
                        viewFirstName.visibility = View.GONE
                        imgFirstName.visibility = View.GONE
                        imgSecondName.visibility = View.GONE
                        tvFirstNameItem.visibility = View.VISIBLE
                        tvFirstNameItem.text = item.tv_firstName_item
                    }

                    if (adapterPosition >= itemList.size - 3) {
                        binding.tvSecondNameItem.apply {
                            visibility = View.VISIBLE
                            text = item.tv_secondName_item
                        }
                    } else {
                        binding.tvSecondNameItem.visibility = View.GONE
                    }
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