package ru.kraz.shareimage.domain

import java.io.File

interface MainRepository {
    suspend fun uploadFile(file: File): ResultFDS<Int>
    suspend fun fetchImages(): ResultFDS<List<String>>
}