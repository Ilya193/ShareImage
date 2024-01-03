package ru.kraz.shareimage.domain

sealed class ResultFDS<out T> {

    data class Success<T>(
        val data: T
    ): ResultFDS<T>()

    data class Error(
        val e: Exception
    ): ResultFDS<Nothing>()

}