package id.indocyber.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter("custom:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .placeholder(R.drawable.no_image_foreground)
                .into(imageView)
        }
    }

    @BindingAdapter("custom:loadImageCircled")
    @JvmStatic
    fun loadImageCircled(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(imageView)
        }
    }
}