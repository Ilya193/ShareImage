package ru.kraz.shareimage.data

import ru.kraz.shareimage.ToMapper

data class ImageCloud(
    val url: String
): ToMapper<ImageData> {
    override fun map(): ImageData = ImageData(url)
}