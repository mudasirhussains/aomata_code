package com.example.codingexample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codingexample.R
import com.example.codingexample.interfaces.ItemClickListener
import com.example.codingexample.models.Hit


class ImageListingAdapter(
    private val mCtx: Context,
    private val mEditorsList: ArrayList<Hit>?,
    private val onClick: ItemClickListener
) : RecyclerView.Adapter<ImageListingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_listitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mList = mEditorsList!![position]
        holder.cardItem.setOnClickListener {
            onClick.onImageCLicked(mList.largeImageURL)
        }
        Glide.with(mCtx).load(mList.previewURL)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imagePreview)
    }

    override fun getItemCount(): Int {
        return mEditorsList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem = itemView.findViewById(R.id.cardItem) as ConstraintLayout
        val imagePreview = itemView.findViewById(R.id.imagePreview) as ImageView
    }
}