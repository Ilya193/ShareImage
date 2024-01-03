package ru.kraz.shareimage.domain

import java.io.File

class UploadFileUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(file: File) = repository.uploadFile(file)
}