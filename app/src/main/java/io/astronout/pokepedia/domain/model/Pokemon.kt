package io.astronout.pokepedia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String
): Parcelable {

    fun getIdString() = String.format("#%03d", id)
}
