package com.instance.dataxbranch.utils

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
suspend fun AlertDialog.await(
    positiveText: String,
    negativeText: String
) = suspendCancellableCoroutine<Boolean> { cont ->
    //val ok:onCancellation: ((cause: Throwable) -> Unit)?)
    val listener = DialogInterface.OnClickListener { _, which ->
        if (which == AlertDialog.BUTTON_POSITIVE) cont.tryResume(false)
        else if (which == AlertDialog.BUTTON_NEGATIVE) cont.tryResume(false)
    }

    setButton(AlertDialog.BUTTON_POSITIVE, positiveText, listener)
    setButton(AlertDialog.BUTTON_NEGATIVE, negativeText, listener)

    // we can either decide to cancel the coroutine if the dialog
    // itself gets cancelled, or resume the coroutine with the
    // value [false]
    setOnCancelListener { cont.cancel() }

    // if we make this coroutine cancellable, we should also close the
    // dialog when the coroutine is cancelled
    cont.invokeOnCancellation { dismiss() }

    // remember to show the dialog before returning from the block,
    // you won't be able to do it after this function is called!
    show()
}