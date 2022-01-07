package io.astronout.pokepedia.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
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
fun Context.getColorStateListResource(@ColorRes color: Int) = ContextCompat.getColorStateList(this, color)
fun Context.getDrawableResource(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

fun TextView.setTextColorResource(@ColorRes colorId: Int) {
    setTextColor(context.getColorResource(colorId))
}

fun MaterialCardView.setCardBackgroundColorResource(colorId: Int) {
    setCardBackgroundColor(
        context.getColorResource(colorId)
    )
}

fun String.getPokemonId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun Int.getPokemonImage() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$this.png"
//fun Int.getPokemonImage() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$this.png"

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

fun String.getPokemonTypeColor(): Int {
    return when (this) {
        "bug" -> R.color.type_bug
        "dark" -> R.color.type_dark
        "dragon" -> R.color.type_dragon
        "electric" -> R.color.type_electric
        "fairy" -> R.color.type_fairy
        "fighting" -> R.color.type_fighting
        "fire" -> R.color.type_fire
        "flying" -> R.color.type_flying
        "ghost" -> R.color.type_ghost
        "grass" -> R.color.type_grass
        "ground" -> R.color.type_ground
        "ice" -> R.color.type_ice
        "normal" -> R.color.type_normal
        "poison" -> R.color.type_poison
        "psychic" -> R.color.type_psychic
        "rock" -> R.color.type_rock
        "steel" -> R.color.type_steel
        "water" -> R.color.type_water
        else -> R.color.gray4
    }
}

fun String.getPokemonTypeIcon(): Int {
    return when (this) {
        "bug" -> R.drawable.ic_bug
        "dark" -> R.drawable.ic_dark
        "dragon" -> R.drawable.ic_dragon
        "electric" -> R.drawable.ic_electric
        "fairy" -> R.drawable.ic_fairy
        "fighting" -> R.drawable.ic_fighting
        "fire" -> R.drawable.ic_fire
        "flying" -> R.drawable.ic_flying
        "ghost" -> R.drawable.ic_ghost
        "grass" -> R.drawable.ic_grass
        "ground" -> R.drawable.ic_ground
        "ice" -> R.drawable.ic_ice
        "normal" -> R.drawable.ic_normal
        "poison" -> R.drawable.ic_poison
        "psychic" -> R.drawable.ic_psychic
        "rock" -> R.drawable.ic_rock
        "steel" -> R.drawable.ic_steel
        "water" -> R.drawable.ic_water
        else -> R.drawable.ic_bug
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

fun Fragment.changeStatusBarColor(@ColorRes color: Int?) {
    val window = requireActivity().window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = requireContext().getColorResource(color ?: R.color.colorPrimary)
}

fun ChipGroup.addChip(label: String) {
    Chip(context).apply {
        id = View.generateViewId()
        text = label.capitalize()
        setChipDrawable(ChipDrawable.createFromAttributes(context, null, 0, R.style.Pokepedia_Chip))
        isCloseIconVisible = false
        setChipIconResource(label.getPokemonTypeIcon())
        chipIconTint = context.getColorStateListResource(R.color.light)
        isClickable = false
        isCheckable = false
        isFocusable = false
        textStartPadding = 0F
        textEndPadding = 8F
        chipSpacingHorizontal = 0
        setTextAppearance(R.style.PokepediaTypographyStyles_Body2_Medium)
        setTextColorResource(R.color.light)
        chipBackgroundColor = context.getColorStateListResource(label.getPokemonTypeColor())
        addView(this)
    }
}

fun Context.getColoredString(text: CharSequence, @ColorRes colorId: Int): Spannable {
    val spannable = SpannableString(text)
    spannable.setSpan(
        ForegroundColorSpan(getColorResource(colorId)),
        0,
        spannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}