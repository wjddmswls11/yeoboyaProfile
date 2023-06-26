import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yeoboyaprofile.R
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.yeoboyaprofile.databinding.ItemProfilePictureBinding

class MyViewPagerAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<MyViewPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile_picture, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actualPosition = position % items.size
        Glide.with(holder.imageView.context)
            .load(items[actualPosition])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return 10000
    }
}