package ru.kraz.shareimage.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.kraz.shareimage.databinding.ItemBinding

class ImagesAdapter : ListAdapter<ImageUi, ImagesAdapter.ImagesViewHolder>(DiffUtil()) {
    class ImagesViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: ImageUi) {
            view.image.load(item.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtil : DiffUtil.ItemCallback<ImageUi>() {
    override fun areItemsTheSame(oldItem: ImageUi, newItem: ImageUi): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ImageUi, newItem: ImageUi): Boolean =
        oldItem == newItem

}