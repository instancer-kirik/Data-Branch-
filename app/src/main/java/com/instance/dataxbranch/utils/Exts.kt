package com.instance.dataxbranch.utils

import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavOptionsBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.instance.dataxbranch.core.Constants.TAG
import com.instance.dataxbranch.NavGraphs
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
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

fun DestinationsNavigator.navigate(//this isn't taking. idk why

    direction: Direction? = null,
    rte: String? = null,
    custom:String
) {
    //save the direction.route as current or find a way to get it maybe
    Log.d(TAG, "CALLED EXT FUN IN EXTS ON NAVIGATE $custom")
    if (direction != null) {
        navigate(direction.route)
    }
    else if (rte != null){
        navigate(rte)
    }
    else{
        Log.d(TAG, "Defaulted $custom")
        navigate( NavGraphs.root.route,)
    }
}