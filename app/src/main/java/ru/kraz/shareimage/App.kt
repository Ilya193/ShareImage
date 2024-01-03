package ru.kraz.shareimage

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kraz.shareimage.data.MainRepositoryImpl
import ru.kraz.shareimage.data.MainService
import ru.kraz.shareimage.domain.FetchImagesUseCase
import ru.kraz.shareimage.domain.ImageDomain
import ru.kraz.shareimage.domain.MainRepository
import ru.kraz.shareimage.domain.UploadFileUseCase
import ru.kraz.shareimage.presentation.DrawingViewModel
import ru.kraz.shareimage.presentation.ImageUi
import ru.kraz.shareimage.presentation.ImagesViewModel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module)
        }
    }
}

val module = module {
    viewModel<DrawingViewModel> {
        DrawingViewModel(get())
    }

    viewModel<ImagesViewModel> {
        ImagesViewModel(get())
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

/*    factory<ResourceProvider> {
        ResourceProvider.Base(get())
    }*/

    factory<UploadFileUseCase> {
        UploadFileUseCase(get())
    }

    factory<FetchImagesUseCase> {
        FetchImagesUseCase(get())
    }

    single<MainService> {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.7:8080")
            .addConverterFactory(get())
            .build()
            .create(MainService::class.java)
    }

    factory<MainRepository> {
        MainRepositoryImpl(get())
    }
}