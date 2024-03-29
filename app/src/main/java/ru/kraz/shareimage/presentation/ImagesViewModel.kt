package ru.kraz.shareimage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kraz.shareimage.domain.FetchImagesUseCase
import ru.kraz.shareimage.domain.ImageDomain
import ru.kraz.shareimage.domain.ResultFDS

class ImagesViewModel(
    private val fetchImagesUseCase: FetchImagesUseCase,
) : ViewModel() {

    private val _images = MutableLiveData<ImagesUiState>()
    val images: LiveData<ImagesUiState> get() = _images

    fun fetchImages() = viewModelScope.launch {
        _images.value = ImagesUiState.Loading
        when (val res = fetchImagesUseCase()) {
            is ResultFDS.Success -> _images.value = ImagesUiState.Success(res.data.map { ImageUi(it) })
            is ResultFDS.Error -> _images.value = ImagesUiState.Error
        }
    }
}

data class ImageUi(
    val url: String,
)

sealed interface ImagesUiState {
    data class Success(val data: List<ImageUi>) : ImagesUiState
    data object Error : ImagesUiState
    data object Loading : ImagesUiState
}