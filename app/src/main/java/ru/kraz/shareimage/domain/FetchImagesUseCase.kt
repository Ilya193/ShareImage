package ru.kraz.shareimage.domain

class FetchImagesUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(): ResultFDS<List<String>> = repository.fetchImages()
}