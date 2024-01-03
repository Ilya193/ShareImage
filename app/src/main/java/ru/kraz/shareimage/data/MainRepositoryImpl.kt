package ru.kraz.shareimage.data

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.kraz.shareimage.domain.ImageDomain
import ru.kraz.shareimage.domain.MainRepository
import ru.kraz.shareimage.domain.ResultFDS
import java.io.File

class MainRepositoryImpl(
    private val service: MainService,
) : MainRepository {
    override suspend fun uploadFile(file: File): ResultFDS<Int> {
        return try {
            ResultFDS.Success(
                service.upload(
                    MultipartBody.Part.createFormData(
                        "file",
                        file.name,
                        file.asRequestBody("multipart/form-data".toMediaType())
                    ), 0
                )
            )
        } catch (e: Exception) {
            ResultFDS.Error(e)
        }
    }

    override suspend fun fetchImages(): ResultFDS<List<String>> {
        return try {
            ResultFDS.Success(service.fetchImages())
        } catch (e: Exception) {
            ResultFDS.Error(e)
        }
    }
}