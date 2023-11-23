package com.task.currexchangerates.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.task.currexchangerates.BuildConfig
import okhttp3.CertificatePinner
import org.json.JSONArray
import org.json.JSONObject


fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
    when (val value = get(it)) {
        is JSONObject -> value.toMap()
        is JSONArray -> value.toList()
        else -> value
    }
}

fun JSONArray.toList(): List<*> = (0 until length()).asSequence().map {
    when (val value = get(it)) {
        is JSONObject -> value.toMap()
        is JSONArray -> value.toList()
        else -> value
    }
}.toList()

/**
 * Extension method to show toast for Context.
 */
fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) = this?.let { Toast.makeText(it, textId, duration).show() }

/*
* SSL Pinning Config
* */
object SSLPinning {
    fun getPinnedCertificate(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(BuildConfig.BASE_URL , "sha256/TESTTESTTESTTESTTESTTESTTESTTESTTEST")
            // more certificates or public keys here if needed
            .build()
    }

}