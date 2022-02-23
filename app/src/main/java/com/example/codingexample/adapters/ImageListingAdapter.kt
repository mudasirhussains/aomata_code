package com.example.codingexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codingexample.R
import com.example.codingexample.databinding.GridListitemBinding
import com.example.codingexample.interfaces.ItemClickListener
import com.example.codingexample.models.Hit

class ImageListingAdapter : RecyclerView.Adapter<ImageListingAdapter.MyViewHolder>() {
    private var items = ArrayList<Hit>()
    private lateinit var onClick: ItemClickListener

    fun setDataList(data: ArrayList<Hit>, listener: ItemClickListener) {
        this.items = data
        this.onClick = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageListingAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GridListitemBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    class MyViewHolder(private val binding: GridListitemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Hit,/* callback: (String) -> Unit*/clickListener: ItemClickListener) {
            binding.model = data
            binding.executePendingBindings()
            binding.cardItem.setOnClickListener {
                clickListener.onImageCLicked(data.largeImageURL)
            }
        }
    }

    companion object {

        @BindingAdapter("loadGlideImage")
        @JvmStatic
        fun loadImage(thumbImage: ImageView, url: String) {
            Glide.with(thumbImage)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(thumbImage)
        }

    }


}