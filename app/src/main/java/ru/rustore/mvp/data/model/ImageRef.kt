package ru.rustore.mvp.data.model

import androidx.annotation.DrawableRes

sealed interface ImageRef {
    data class Resource(@DrawableRes val resId: Int) : ImageRef
    data class Url(val url: String) : ImageRef
}
