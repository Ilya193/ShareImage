package ru.kraz.shareimage.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kraz.shareimage.domain.ResultFDS
import ru.kraz.shareimage.domain.UploadFileUseCase
import java.io.File

class DrawingViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
) : ViewModel() {

    private val _uploadFile = MutableLiveData<EventWrapper<DrawingUiState>>()
    val uploadFile: LiveData<EventWrapper<DrawingUiState>> get() = _uploadFile

    fun uploadFile(file: File) = viewModelScope.launch {
        when (uploadFileUseCase(file)) {
            is ResultFDS.Success -> _uploadFile.value = EventWrapper.Single(DrawingUiState.Success)
            is ResultFDS.Error -> _uploadFile.value = EventWrapper.Single(DrawingUiState.Error)
        }
    }
}

sealed interface DrawingUiState {
    data object Success : DrawingUiState
    data object Error : DrawingUiState
}