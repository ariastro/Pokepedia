package io.astronout.pokepedia.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.card.MaterialCardView
import io.astronout.pokepedia.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.util.*

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.delayJob(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit
) = CoroutineScope(dispatcher).launch {
    delay(durationInMillis)
    block()
}

fun wait(delay: Long = 300, action: () -> Unit) =
    Handler(Looper.getMainLooper()).postDelayed(action, delay)

private val shimmer =
    Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
        .setDuration(1800) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.7F) //the alpha of the underlying children
        .setHighlightAlpha(0.6F) // the shimmer alpha amount
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

// This is the placeholder for the imageView
val shimmerDrawable = ShimmerDrawable().apply {
    setShimmer(shimmer)
}

fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.getDefault()
    ) else it.toString()
}

fun Context.getColorResource(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun MaterialCardView.setCardBackgroundColorResource(colorId: Int) {
    setCardBackgroundColor(
        context.getColorResource(colorId)
    )
}

fun String.getPokemonId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.getPokemonImage() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${this.getPokemonId()}.png"

fun String.getPokemonTypeBackground(): Int {
    return when (this) {
        "bug" -> R.color.background_type_bug
        "dark" -> R.color.background_type_dark
        "dragon" -> R.color.background_type_dragon
        "electric" -> R.color.background_type_electric
        "fairy" -> R.color.background_type_fairy
        "fighting" -> R.color.background_type_fighting
        "fire" -> R.color.background_type_fire
        "flying" -> R.color.background_type_flying
        "ghost" -> R.color.background_type_ghost
        "grass" -> R.color.background_type_grass
        "ground" -> R.color.background_type_ground
        "ice" -> R.color.background_type_ice
        "normal" -> R.color.background_type_normal
        "poison" -> R.color.background_type_poison
        "psychic" -> R.color.background_type_psychic
        "rock" -> R.color.background_type_rock
        "steel" -> R.color.background_type_steel
        "water" -> R.color.background_type_water
        else -> R.color.gray4
    }
}

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

fun View.onClick(block: View.OnClickListener) = setOnClickListener(block)

fun Fragment.changeStatusBarColor(@ColorRes color: Int) {
    val window = requireActivity().window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = requireContext().getColorResource(color)
}