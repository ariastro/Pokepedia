package io.astronout.pokedexin.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun Fragment.delayJob(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit
) = CoroutineScope(dispatcher).launch {
    delay(durationInMillis)
    block()
}

fun wait(delay: Long = 300, action: () -> Unit) = Handler(Looper.getMainLooper()).postDelayed(action, delay)