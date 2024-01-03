package ru.kraz.shareimage.data

import ru.kraz.shareimage.ToMapper
import ru.kraz.shareimage.domain.ImageDomain

data class ImageData(
    val url: String
): ToMapper<ImageDomain> {
    override fun map(): ImageDomain = ImageDomain(url)
}