package com.example.yeoboyaprofile

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class PhotoVideoAdapter(private val array2: List<String>) :RecyclerView.Adapter<PhotoVideoAdapter.PhotoVideoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo_video, parent, false)
        return PhotoVideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return array2.size
    }

    override fun onBindViewHolder(holder: PhotoVideoViewHolder, position: Int) {
        val imageUrl = array2[position]
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imgPhotoVideo)

        // 마지막 두 요소일 경우에만 imgPlayIcon을 보이게 설정
        if (position >= array2.size - 2) {
            holder.imgPlayIcon.visibility = View.VISIBLE
        } else {
            holder.imgPlayIcon.visibility = View.INVISIBLE
        }

        //여기서 마진을 변경합니다.
        updateCardViewMargin(holder.itemView, position)
    }

    private fun updateCardViewMargin(view: View, position: Int) {
        val cardView = view.findViewById<CardView>(R.id.cd_photovideo_item)
        val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams

        //여기에 마진을 설정하세요.
        val leftMargin = when(position) {
            0, 3 ->0.dpToPx(view.context)
            else -> 9.dpToPx(view.context)
        }

        val topMargin = when(position) {
            0,1,2 -> 0.dpToPx(view.context)
            else -> 8.dpToPx(view.context)
        }

        layoutParams.setMargins(leftMargin, topMargin, layoutParams.rightMargin, layoutParams.bottomMargin)
        cardView.layoutParams = layoutParams

    }





    class PhotoVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhotoVideo: ImageView = itemView.findViewById(R.id.img_photo_video)
        val imgPlayIcon : ImageView = itemView.findViewById(R.id.icon_play)
    }


    private fun Int.dpToPx(context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }


}